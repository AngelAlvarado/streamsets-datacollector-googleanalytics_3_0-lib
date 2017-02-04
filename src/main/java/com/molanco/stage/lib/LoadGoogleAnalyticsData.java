package com.molanco.stage.lib;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.GaData;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Load/Query data from to query Google Analytics using Auth2 authentication and user settings
 */
public class LoadGoogleAnalyticsData {

    // Instantiate Analytics object.
    private static Analytics analytics;

    private static String getFirstProfileId(Analytics analytics) throws IOException {
        // Get the first view (profile) ID for the authorized user.
        String profileId = null;
        // Query for the list of all accounts associated with the service account.
        Accounts accounts = analytics.management().accounts().list().execute();

        if (accounts.getItems().isEmpty()) {
            System.err.println("No accounts found");
        } else {
            String firstAccountId = accounts.getItems().get(0).getId();

            // Query for the list of properties associated with the first account.
            com.google.api.services.analytics.model.Webproperties properties = analytics.management().webproperties()
                    .list(firstAccountId).execute();

            if (properties.getItems().isEmpty()) {
                System.err.println("No Webproperties found");
            } else {
                String firstWebpropertyId = properties.getItems().get(0).getId();

                // Query for the list views (profiles) associated with the property.
                com.google.api.services.analytics.model.Profiles profiles = analytics.management().profiles()
                        .list(firstAccountId, firstWebpropertyId).execute();

                if (profiles.getItems().isEmpty()) {
                    System.err.println("No views (profiles) found");
                } else {
                    // Return the first (view) profile associated with the property.
                    profileId = profiles.getItems().get(0).getId();
                }
            }
        }
        return profileId;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.security.GeneralSecurityException
     */
    public static GaData loadData(String start_date, String end_date, String metrics, String dimensions) throws IOException, GeneralSecurityException {

        // Authorize and generate Google Analytics object.
        analytics = OAuth2Client.getAnalyticsInstance();

        // Use first account in GA.
        // Assuming that using the first account is ok
        // @todo improve this part.
        String profileId = getFirstProfileId(analytics);

        // Query Content.
        GaData results = analytics.data().ga()
                .get("ga:" + profileId, start_date, end_date, metrics).setDimensions(dimensions)
                .execute();


        // Logging Profile ID; If this ID shows up in your GA dashboard it means everything went good
        System.out.println("Google Analytics Account ID" + profileId);
        return results;
    }


}