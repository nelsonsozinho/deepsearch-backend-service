package com.axreng.backend.config;

public class Environment {

    public static final String BASE_URL = "BASE_URL";

    public static final String AXRENG_URL = "http://hiring.axreng.com/";

    public static final String DOMAIN = System.getenv(BASE_URL) == null ? AXRENG_URL : System.getenv(BASE_URL);

}
