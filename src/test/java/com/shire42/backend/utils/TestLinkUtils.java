package com.shire42.backend.utils;

import com.shire42.backend.engine.config.Environment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLinkUtils {


    @Test
    public void testStripLink() {
        final String link = "<a href=\"https://google.com\"/>";
        final String linkStripped = LinkUtils.stripeLink(link);
        assertNotNull(linkStripped);
        assertEquals(linkStripped, "https://google.com");
    }

    @Test
    public void testLinkIsNotFromDefinedDomain() {
        final String link = "https://kernel.org";
        assertFalse(LinkUtils.isLinkFromDomain(link));
    }

    @Test
    public void testLinkIsFromDomainDefined() {
        final String link = "http://hiring.axreng.com/manpages/strcmp.html";
        assertTrue(LinkUtils.isLinkFromDomain(link));
    }

    @Test
    public void testGetLinkAccurate() {
        final String link = "../strcmp.html";
        assertEquals(Environment.DOMAIN + "strcmp.html", LinkUtils.accurateLink(link));
    }

}
