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
 * Authenticator that handles the authentication with myanimelist.net
 */
public class MalAuthenticator implements Authenticator{

    /**
     * Checks if the user is authenticated on myanimelist.net
     * @param username the user's username
     * @param password the user's password
     * @return true if the user is authenticated, false otherwise
     * @throws IOException if a connection error instead of an authentication error occured
     */
    @Override
    public boolean isAuthenticated(String username, String password) throws IOException {
        HttpHandler handler = new HttpHandler("https://myanimelist.net/api/account/verify_credentials.xml");
        handler.setBasicAuthentication(username, password);
        handler.setMethod("GET");
        handler.connect();
        return !handler.getResponse().equals("");
    }
}
