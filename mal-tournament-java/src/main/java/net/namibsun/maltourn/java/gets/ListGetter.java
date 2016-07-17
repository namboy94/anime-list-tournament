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

package net.namibsun.maltourn.java.gets;

import net.namibsun.maltourn.java.http.HttpHandler;
import net.namibsun.maltourn.java.objects.AnimeSeries;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that handles fetching the user's anime list data.
 */
public class ListGetter {

    /**
     * Gets the list data for a myanimelist.net user
     * @param username the username for which the list data should be fetched
     * @return the list data as an ArrayList of AnimeSeries objects
     */
    public static Set<AnimeSeries> getList(String username) {
        Set<AnimeSeries> series = new HashSet<>();

        String list =  HttpHandler.getWithAuth(
                "http://myanimelist.net/malappinfo.php?status=all&type=anime&u=" + username, username);

        for (String xmlData: list.split("<anime>")) {
            if (xmlData.contains("<my_status>2</my_status>")) {  //Status 2 = Completed
                series.add(new AnimeSeries(xmlData.split("</anime>")[0]));  //Get XML data from inside <anime> tags
            }
        }

        return series;
    }

}
