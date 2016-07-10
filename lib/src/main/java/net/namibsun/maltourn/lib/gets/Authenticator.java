package net.namibsun.maltourn.lib.gets;

import net.namibsun.maltourn.lib.http.HttpHandler;

/**
 * Class that checks if a user is authenticated on myanimelist.net
 */
public class Authenticator {

    /**
     * Chacks the authentication of the user
     * @param username the user's user name
     * @param password the user's password
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated(String username, String password) {
        String authUrl = "http://myanimelist.net/api/account/verify_credentials.xml";
        String authenticationResponse = HttpHandler.getWithAuth(authUrl, username + ":" + password);
        return !authenticationResponse.equals("");
    }

}
