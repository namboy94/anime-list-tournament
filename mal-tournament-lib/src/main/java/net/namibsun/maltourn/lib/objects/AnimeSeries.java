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

package net.namibsun.maltourn.lib.objects;

import java.io.IOException;

/**
 * Class that models a myanimelist.net Anime Series
 */
@SuppressWarnings("WeakerAccess")
public abstract class AnimeSeries {

    /**
     * Converts a String to an int, and if the String is not parseable, the result is -1
     * @param number the String to parse
     * @return the parsed integer value
     */
    protected int convertToInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Method that sets the score of the anime
     * @param score the score
     * @param username the user whose score should be change
     * @param password the user's password
     * @throws IOException if the connection failed
     */
    public abstract void setScore(int score, String username, String password) throws IOException;

}
