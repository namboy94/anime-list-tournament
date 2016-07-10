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
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.ArrayList;

/**
 * Created by hermann on 7/9/16.
 */
public class ListGetter {

    private String authentication;
    private String user;

    public ListGetter(String user, String password) {
        this.authentication = user + ":" + password;
        this.user = user;
    }

    public ArrayList<AnimeSeries> getList() {
        ArrayList<AnimeSeries> series = new ArrayList<>();
        String list =  HttpHandler.getWithAuth("http://myanimelist.net/malappinfo.php?status=all&type=anime&u=" + this.user, this.authentication);
        for (String xmlData: list.split("<anime>")) {
            if (xmlData.contains("<my_status>2</my_status>")) {
                series.add(new AnimeSeries(xmlData.split("</anime>")[0]));
            }
        }
        return series;
    }

}
