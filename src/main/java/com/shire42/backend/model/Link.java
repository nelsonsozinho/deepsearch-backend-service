package com.shire42.backend.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Link implements Entity {

    private String id;
    private final String link;
    private String htmlContent;
    private LocalDateTime updatedTime;

    public Link(
            final String link,
            final String htmlContent
    ) {
        this.link = link;
        this.htmlContent = htmlContent;
        this.updatedTime = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getLink() {
        return link;
    }



}

