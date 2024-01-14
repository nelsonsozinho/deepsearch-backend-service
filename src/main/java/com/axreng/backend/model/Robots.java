package com.axreng.backend.model;

import java.util.HashSet;
import java.util.Set;

public class Robots {

    private final Set<String> disallows;

    private final Set<String> sitemaps;

    private final String userAgent;

    private final Long crawlDelay;

    public Robots(final Builder builder) {
        this.disallows = builder.disallows;
        this.sitemaps = builder.sitemaps;
        this.userAgent = builder.userAgent;
        this.crawlDelay = builder.crawlDelay;
    }

    public Set<String> getDisallows() {
        return disallows;
    }

    public Set<String> getSitemaps() {
        return sitemaps;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Long getCrawlDelay() {
        return crawlDelay;
    }

    public static class Builder {

        private Set<String> disallows;
        private Set<String> sitemaps;
        private String userAgent;
        private Long crawlDelay;

        private Builder builder;

        public Builder() {
            this.disallows = new HashSet<>();
            this.sitemaps = new HashSet<>();
        }

        public Builder disallows(final Set<String> disallows) {
            this.disallows = disallows;
            return this;
        }

        public Builder sitemap(final Set<String> sitemap) {
            this.sitemaps = sitemap;
            return this;
        }

        public Builder useAgent(final String useAgent) {
            this.userAgent = useAgent;
            return this;
        }

        public Builder crawlDelay(final Long crawlDelay) {
            this.crawlDelay = crawlDelay;
            return this;
        }

        public Builder addSitemap(final String siteMap) {
            this.sitemaps.add(siteMap);
            return this;
        }

        public Builder addDisallow(final String disallow) {
            this.disallows.add(disallow);
            return this;
        }

        public Robots build() {
            return new Robots(this);
        }

    }

}


