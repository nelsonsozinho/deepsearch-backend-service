package com.axreng.backend.client;

import com.axreng.backend.model.Task;
import com.axreng.backend.util.LinkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkCrawler {

    private static Logger log = LoggerFactory.getLogger(LinkCrawler.class);

    private final Task task;

    public LinkCrawler(final Task task) {
        this.task = task;
    }

    /**
     * Return the steps until find the term
     * @param link
     * @param term
     * @return a link where was find the term or null when not found
     */
    public String searchTerm(final String link, final String term) {
        return deepVisitLink(link, term);
    }

    /**
     * Return the link that the term was found
     * @param link
     * @param term
     * @return
     */
    private String deepVisitLink(final String link, final String term) {
        final String linkStriped = LinkUtils.stripeLink(link);


        if(!LinkUtils.isLinkFromDomain(linkStriped)) {
            log.info("Out of domain: " + linkStriped);
            return null;
        }

        final String accurateLink = LinkUtils.accurateLink(linkStriped);
        if(task.getUrlVisited().contains(accurateLink)) {
            return "";
        }

        log.info("Visit link " + linkStriped);
        final String htmlContent = getContent(visit(accurateLink));

        final List<String> deepLinks = getLink(htmlContent);
        final String content = stripHtmlContent(htmlContent);

        this.task.addUrlVisited(accurateLink);

        if(!content.isEmpty()) {
            if(content.contains(term)) {
                log.info("Process " + task.getId() + " find term in link " + accurateLink);
                return link;
            }
        }

        if(!deepLinks.isEmpty()) {
            deepLinks.forEach(deepLink -> {
                deepVisitLink(deepLink, term);
            });
        }

        return null;
    }

    private InputStream visit(final String link) {
        final URL objUrl;

        try {
            objUrl = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {

            final HttpURLConnection urlConnection = (HttpURLConnection) objUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            return urlConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return the HTML content
     *
     * @param inputStreamContent
     * @return strin with the HTML content
     */
    private String getContent(final InputStream inputStreamContent) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamContent));
        final StringBuilder htmlContent = new StringBuilder();
        String content = "";

        try {
            while ((content = bufferedReader.readLine()) != null) {
                htmlContent.append(content);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return htmlContent.toString();
    }

    private List<String> getLink(final String htmlContent) {
        final List<String> links = new ArrayList<>();
        final Matcher matcher = Pattern.compile("<a\\s+[^>]*href\\s*=\\s*\"([^\"]*)\"[^>]*>").matcher(htmlContent);

        while(matcher.find()) {
            links.add(matcher.group());
        }

        return links;
    }

    /**
     * Stripe the HTML content and return just string text
     * @param content
     * @return string
     */
    private String stripHtmlContent(final String content) {
        final String regex = "<[^>]*>";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll("");
    }

}
