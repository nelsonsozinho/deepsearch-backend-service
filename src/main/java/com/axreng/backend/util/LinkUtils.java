package com.axreng.backend.util;

import com.axreng.backend.config.Environment;

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
        } else {
            return true;
        }
    }

    public static String accurateLink(final String link) {
        if( !(link.contains("http") || link.contains("https")) ) {
            return Environment.DOMAIN + link;
        }

        return link;
    }

}
