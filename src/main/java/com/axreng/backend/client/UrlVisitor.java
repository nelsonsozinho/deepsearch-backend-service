package com.axreng.backend.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlVisitor {

    /**
     * Return the steps until find the term
     * @param url
     * @param term
     * @return
     * @throws IOException
     */
    public List<String> searchTerm(final String url, final String term) throws IOException {
        boolean deepDive = true;


        return null;
    }

    private void deepVisitLink(final String link, final String term) {
        final List<String> deepLinks = visitList(link, term);
        if(!deepLinks.isEmpty()) {
            deepLinks.forEach(deepLink -> {
                deepVisitLink(deepLink, term);
            });
        }
    }

    private List<String> visitList(final String link,  final String term) {
        final List<String> newLinksToVisit = new ArrayList<>();
        URL objUrl;

        try {
            objUrl = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            final HttpURLConnection urlConnection = (HttpURLConnection) objUrl.openConnection();

            urlConnection.setRequestMethod("GET");

            final InputStream inputStream = urlConnection.getInputStream();
            final String content = getContent(inputStream);

            newLinksToVisit.addAll(getLink(content));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return newLinksToVisit;
    }

    private String getContent(final InputStream inputStreamContent) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamContent));
        final StringBuilder htmlContent = new StringBuilder();
        String content = "";

        while ((content = bufferedReader.readLine()) != null) {
            htmlContent.append(content);
        }

        return stripHtmlContent(htmlContent.toString());
    }

    private List<String> getLink(final String htmlContent) {
        final List<String> links = new ArrayList<>();
        final Matcher matcher = Pattern.compile("<a\\s+[^>]*href\\s*=\\s*\"([^\"]*)\"[^>]*>").matcher(htmlContent);

        while(matcher.find()) {
            links.add(matcher.group());
        }

        return links;
    }

    private String stripHtmlContent(final String content) {
        final String regex = "<[^>]*>";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll("");
    }

}
