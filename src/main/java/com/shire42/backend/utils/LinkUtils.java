package com.shire42.backend.utils;

import com.shire42.backend.engine.config.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkUtils {

    public static String stripeLink(final String link) {
        if(!link.contains("href"))
            return link;

        final Matcher matcher =  Pattern.compile("href=\"(.*?)\"").matcher(link);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static Boolean isLinkFromDomain(final String link) {
        final String linkCase = link.toLowerCase();
        if(linkCase.contains("http") ||  linkCase.contains("https")) {
            return linkCase.contains(Environment.DOMAIN);
        } else if(link.contains("mailto")) {
            return false;
        } else if(link.contains("ftp")) {
            return false;
        } else return !link.contains("sftp");
    }

    public static String accurateLink(final String link) {
        if( !(link.contains("http") || link.contains("https")) ) {
            String cleanLink = link.replace("../", "");
            return Environment.DOMAIN + cleanLink;
        }

        return link;
    }

    /**
     * Return the HTML content
     *
     * @param inputStreamContent
     * @return string with the HTML content
     */
    public static String getContent(final InputStream inputStreamContent) {
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

    public static List<String> getLink(final String htmlContent) {
        final List<String> links = new ArrayList<>();
        final Matcher matcher = Pattern.compile("<a\\s+[^>]*href\\s*=\\s*\"([^\"]*)\"[^>]*>").matcher(htmlContent);

        while(matcher.find()) {
            links.add(matcher.group());
        }

        return links;
    }

}
