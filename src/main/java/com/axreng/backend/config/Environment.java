package com.axreng.backend.config;

import java.util.Objects;

public class Environment {

    public static final String BASE_URL = "BASE_URL";

    public static final String AXRENG_URL = "http://hiring.axreng.com/";

    public static final String DOMAIN = Objects.nonNull(System.getenv(BASE_URL)) ? System.getenv(BASE_URL) : AXRENG_URL;

    public static final Long DELAY = 500L;

}
