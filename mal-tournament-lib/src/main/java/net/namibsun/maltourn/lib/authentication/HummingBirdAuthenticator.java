/*
Copyright 2016 Hermann Krumrey

This file is part of mal-tournament.

    mal-tournament is a program that lets a user pit his watched anime series
    from myanimelist.net against each other in an attempt to determine relative scores
    between the shows.

    mal-tournament is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    mal-tournament is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with mal-tournament. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.maltourn.lib.authentication;

import net.namibsun.maltourn.lib.http.HttpHandler;

import java.io.IOException;

/**
 * Authenticator that handles the authentication with hummingbird.me
 */
public class HummingBirdAuthenticator implements Authenticator{

    /**
     * Checks if the user is authenticated on hummingbird.me
     * @param username the user's username
     * @param password the user's password
     * @return true if the user is authenticated, false otherwise
     * @throws IOException if a connection error instead of an authentication error occured
     */
    @Override
    public boolean isAuthenticated(String username, String password) throws IOException {
        return !this.tryToAuthenticate(username, password).equals("");
    }

    /**
     * Gets the authentication token for a username and password
     * It should be checked if the username/password authenticates correctly using isAuthenticated
     * @param username the username
     * @param password the password
     * @return the authentication token
     * @throws IOException in case a connection error occurs
     */
    public String getAuthToken(String username, String password) throws IOException {
        return this.tryToAuthenticate(username, password).split("\"")[1];
    }

    /**
     * Handles the authentication HTTP request
     * @param username the username to check authentication for
     * @param password the user's password
     * @return the HTTP response
     * @throws IOException in case a connection error occurs
     */
    private String tryToAuthenticate(String username, String password) throws IOException {
        HttpHandler handler = new HttpHandler("http://hummingbird.me/api/v1/users/authenticate");
        handler.setMethod("POST");
        handler.setContentType("application/x-www-form-urlencoded");
        handler.connect();
        return handler.postContent(("username=" + username + "&password=" + password).getBytes());
    }
}
