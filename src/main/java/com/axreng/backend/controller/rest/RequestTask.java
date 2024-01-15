package com.axreng.backend.controller.rest;

import java.io.Serializable;

public class RequestTask implements Serializable {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
