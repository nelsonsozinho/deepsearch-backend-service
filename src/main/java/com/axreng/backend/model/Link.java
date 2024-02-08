package com.axreng.backend.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Link implements Entity {

    private String id;

    private final String link;
    private String htmlContent;

    private LocalDateTime updatedTime;

    public Link(final String link) {
        this.link = link;
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

