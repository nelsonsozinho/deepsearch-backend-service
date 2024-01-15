package com.axreng.backend.engine.crawler;

import com.axreng.backend.engine.config.Environment;
import com.axreng.backend.model.Robots;
import com.axreng.backend.model.Task;
import com.axreng.backend.engine.crawler.parser.RobotsParser;
import com.axreng.backend.utils.LinkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkCrawler {

    private static final Logger log = LoggerFactory.getLogger(LinkCrawler.class);

    private final Task task;

    private final UrlVisitor visitor;

    private Robots robots;


    public LinkCrawler(final Task task) {
        this.visitor = new UrlVisitor();
        this.task = task;
        this.configRobots();
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

        //avoid to visit links visited
        final String accurateLink = LinkUtils.accurateLink(linkStriped);
        if(task.getUrlVisited().contains(accurateLink)) {
            return link;
        }

        //validate robot content
        if(!robotCanGo(accurateLink)) {
            return null;
        }

        final String htmlContent = getContent(
                visitor.visit(
                        accurateLink,
                        robots.getCrawlDelay() == null ? Environment.DELAY : robots.getCrawlDelay()));
        final List<String> deepLinks = getLink(htmlContent);
        this.task.addUrlVisited(accurateLink);

        if(!htmlContent.isEmpty()) {
            if(htmlContent.contains(term)) {
                log.info("Process " + task.getId() + " find term in link " + accurateLink);
                this.task.setLinkResearchFind(link);
            }
        }

        if(!deepLinks.isEmpty()) {
            for(var deepLink : deepLinks) {
                if(Objects.isNull(task.getLinkResearchFind())) {
                    deepVisitLink(deepLink, term);
                }
            }
        }

        return null;
    }

    /**
     * Return the HTML content
     *
     * @param inputStreamContent
     * @return string with the HTML content
     */
    private String getContent(final InputStream inputStreamContent) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamContent));
        final StringBuilder htmlContent = new StringBuilder();

        try {
            String content;
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

    private void configRobots() {
        log.info("Config robots.txt");
        final RobotsParser parser = new RobotsParser();
        this.robots = parser.parserRobots();
    }

    public boolean robotCanGo(final String accurateLink) {
        return this.robots.getDisallows().stream().noneMatch(accurateLink::contains);
    }

}
