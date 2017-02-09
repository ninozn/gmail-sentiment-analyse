package com.incentro.sa;

import java.util.Arrays;
import java.util.Collection;

public class Constant {

    // OAuth variables
    public static final String APPLICATION_NAME = "incentro-signatures";
    public static final String WEBCLIENT_ID = "382820378784-eka8omknqlp5q01mgn4cj0bhhihscs8h.apps.googleusercontent.com";
    public static final String WEBCLIENT_SECRET = "cu4pb2YF2nPZOGIUqwc1BvTG";
    // Webclient scopes
    private static final String scopesString = "https://www.googleapis.com/auth/userinfo.email,https://mail.google.com/,https://www.googleapis.com/auth/cloud-platform";
    public static final Collection<String> WEBCLIENT_SCOPES = Arrays.asList(scopesString.split(","));
}