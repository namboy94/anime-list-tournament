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
