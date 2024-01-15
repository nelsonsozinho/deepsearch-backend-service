package com.axreng.backend.engine.crawler;

import com.axreng.backend.engine.config.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class UrlVisitor {

    private static final Logger log = LoggerFactory.getLogger(UrlVisitor.class);

    public InputStream visit(final String link, final Long delay) {
        final URL objUrl;

        try {
            objUrl = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            //two seconds of delay to void DOS
            if(Objects.nonNull(delay)) {
                Thread.sleep(delay);
            } else {
                Thread.sleep(Environment.DELAY);
            }

            final HttpURLConnection urlConnection = (HttpURLConnection) objUrl.openConnection();
            final int responseCode = urlConnection.getResponseCode();

            log.info("Visit link " + link + ". Response: " + responseCode);

            if(responseCode == HttpURLConnection.HTTP_OK) {
                return urlConnection.getInputStream();
            }

            urlConnection.disconnect();
        } catch (IOException | InterruptedException e) {
            log.error("Error on access page " + link, e);
        }

        return null;
    }

}
