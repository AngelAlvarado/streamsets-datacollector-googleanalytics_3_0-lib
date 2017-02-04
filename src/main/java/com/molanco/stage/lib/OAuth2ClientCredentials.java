package com.molanco.stage.lib;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Esta clase almacena las credenciales y los objetos necesarios en la creacion del objeto GoogleCredential
 *
 * @author Daniel
 */
public class OAuth2ClientCredentials {


    // Instantiate HttpTransport.
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    // Instantiate Json Factory
    private static JsonFactory JSON_FACTORY = new JacksonFactory();

    // .p12 file path, this is configured in the SDC Origin dashboard. .p21 is usually generated from Developers Console when generating a project.
    private static String PATH_P12KEY_SERVICE_ACCOUNT = "";

    // Email account usually generated from Google Developers Console when generating a project.
    private static String SERVICE_ACCOUNT_EMAIL = "";

    // Application name generated from Google Developers Console when generating a project.
    private static String APPLICATION_NAME = "";


    public static HttpTransport getHTTP_TRANSPORT() {
        return HTTP_TRANSPORT;
    }

    public static JsonFactory getJSON_FACTORY() {
        return JSON_FACTORY;
    }

    public static String getPATH_P12KEY_SERVICE_ACCOUNT() {
        return PATH_P12KEY_SERVICE_ACCOUNT;
    }

    public static String getSERVICE_ACCOUNT_EMAIL() {
        return SERVICE_ACCOUNT_EMAIL;
    }

    public static String getAPPLICATION_NAME() {
        return APPLICATION_NAME;
    }


    public static void setAPPLICATION_NAME(String NAME) {
        APPLICATION_NAME = NAME;
    }

    public static void setPATH_P12KEY_SERVICE_ACCOUNT(String ACCOUNT) {
        PATH_P12KEY_SERVICE_ACCOUNT = ACCOUNT;
    }

    public static void setSERVICE_ACCOUNT_EMAIL(String ACCOUNT_EMAIL) {
        SERVICE_ACCOUNT_EMAIL = ACCOUNT_EMAIL;
    }


}