package com.axreng.backend.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Link implements Serializable {

    private final String link;
    private String htmlContent;
    private String strippedContent;

    private LocalDateTime updatedTime;

    public Link(final String link) {
        this.link = link;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getStrippedContent() {
        return strippedContent;
    }

    public void setStrippedContent(String strippedContent) {
        this.strippedContent = strippedContent;
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

