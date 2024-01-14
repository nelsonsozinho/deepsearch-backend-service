package com.axreng.backend.parser;

import com.axreng.backend.client.UrlVisitor;
import com.axreng.backend.config.Environment;
import com.axreng.backend.model.Robots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class RobotsParser {

    private static final Logger log = LoggerFactory.getLogger(RobotsParser.class);

    private final UrlVisitor visitor;

    public RobotsParser() {
        this.visitor = new UrlVisitor();
    }

    public Robots parserRobots() {
        return getContentFromRobots();
    }

    private Robots getContentFromRobots() {
        final String robotsLink = Environment.DOMAIN + "robots.txt";
        final InputStream inputStream = visitor.visit(robotsLink, null);

        if(Objects.isNull(inputStream)) {
            log.info("There is no robots.txt in this website");
            return new Robots.Builder().build();
        }

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        final Robots.Builder builder = new Robots.Builder();

        try {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                fillRobotObject(line, builder);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.build();
    }

    private void fillRobotObject(final String line, final Robots.Builder builder) {
        if(Objects.nonNull(line)) {
            final String lineLowerCase = line.toLowerCase();

            if(!line.trim().startsWith("#") && !line.trim().isEmpty()) {
                final String[] parts = lineLowerCase.split(":",2);

                if(parts.length == 2) {
                    final String directive = parts[0];
                    final String value = parts[1];

                    switch (directive) {
                        case "disallow" -> builder.addDisallow(value);
                        case "sitemap" -> builder.addSitemap(value);
                        case "user-agent" -> builder.useAgent(value);
                        case "crawl-delay" -> builder.crawlDelay(Long.parseLong(value));
                    }
                }
            }
        }
    }

}
