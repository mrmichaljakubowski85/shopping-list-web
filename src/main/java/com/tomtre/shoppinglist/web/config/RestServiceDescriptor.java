package com.tomtre.shoppinglist.web.config;

public class RestServiceDescriptor {
    private static final String SEPARATOR = "/";
    private static final String SERVICE_PATH_PREFIX = "api";
    private static final String API_VERSION_1 = "v1";
    public static final String API_PREFIX = SEPARATOR + SERVICE_PATH_PREFIX;
    public static final String FULL_PATH = API_PREFIX + SEPARATOR + API_VERSION_1;
}
