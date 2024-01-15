package com.axreng.backend.engine.crawler;

import com.axreng.backend.engine.config.Environment;
import com.axreng.backend.engine.crawler.parser.RobotsParser;
import com.axreng.backend.model.Robots;
import com.axreng.backend.model.Task;
import com.axreng.backend.utils.LinkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

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

        final String htmlContent = LinkUtils.getContent(
                visitor.visit(
                        accurateLink,
                        robots.getCrawlDelay() == null ? Environment.DELAY : robots.getCrawlDelay()));
        final List<String> deepLinks = LinkUtils.getLink(htmlContent);
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

    private void configRobots() {
        log.info("Config robots.txt");
        final RobotsParser parser = new RobotsParser();
        this.robots = parser.parserRobots();
    }

    public boolean robotCanGo(final String accurateLink) {
        return this.robots.getDisallows().stream().noneMatch(accurateLink::contains);
    }

}