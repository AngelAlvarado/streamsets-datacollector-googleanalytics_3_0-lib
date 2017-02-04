package com.molanco.stage.lib;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Auth2 authentication with Google
 * <p>
 * Once authentication object if generated, proceed to authenticate and generate a Google Analytics Object - Read Only permissions.
 */
public class OAuth2Client {

    private static GoogleCredential credentialBuilder;

    private static void generateAuthenticationInstance() throws IOException, GeneralSecurityException {
        credentialBuilder = new GoogleCredential.Builder()
                .setTransport(OAuth2ClientCredentials.getHTTP_TRANSPORT())
                .setJsonFactory(OAuth2ClientCredentials.getJSON_FACTORY())
                .setServiceAccountId(OAuth2ClientCredentials.getSERVICE_ACCOUNT_EMAIL())
                .setServiceAccountScopes(Arrays.asList(AnalyticsScopes.ANALYTICS_READONLY))
                .setServiceAccountPrivateKeyFromP12File(new File(OAuth2ClientCredentials.getPATH_P12KEY_SERVICE_ACCOUNT()))
                .build();
    }

    //Authenticate and get Analytics Instance.
    public static Analytics getAnalyticsInstance() throws IOException, GeneralSecurityException {
        generateAuthenticationInstance();
        return new Analytics.Builder(
                OAuth2ClientCredentials.getHTTP_TRANSPORT(), OAuth2ClientCredentials.getJSON_FACTORY(), credentialBuilder)
                .setApplicationName(OAuth2ClientCredentials.getAPPLICATION_NAME())
                .build();
    }

}