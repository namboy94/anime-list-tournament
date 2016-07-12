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

package net.namibsun.maltourn.lib.posts;

import net.namibsun.maltourn.lib.http.HttpHandler;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

/**
 * Class that handles setting the score of an anime
 */
public class ScoreSetter {

    private String authentication;

    /**
     * Creates a new ScoreSetter object, converts the password and username
     * into an authentication string to be used by HttpHandler
     * @param username the username of the user who's score will be changed
     * @param password the password of said user
     */
    public ScoreSetter(String username, String password) {
        this.authentication = username + ":" + password;
    }

    /**
     * Sets the score of an anime series
     * @param series the AnimeSeries object of the series to change the score of
     * @param score the new score, an integer value between 1 and 10
     */
    public void setScore(AnimeSeries series, int score) {
        String url = "http://myanimelist.net/api/animelist/update/" + series.seriesAnimedbId + ".xml";
        String payload = "data=%3Centry%3E%3Cscore%3E" + score + "%3C%2Fscore%3E%3C%2Fentry%3E";
        HttpHandler.postWithAuth(url, authentication, payload);
    }

}
