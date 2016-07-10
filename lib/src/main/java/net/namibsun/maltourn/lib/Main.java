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

package net.namibsun.maltourn.lib;

import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by hermann on 7/9/16.
 */
public class Main {

    public static void main(String[] args) {

        //was 5
        /*
        ListGetter list = new ListGetter("namboy94", "");
        ArrayList<AnimeSeries> series = list.getList();
        for (AnimeSeries serie: series) {
            System.out.println(serie.myScore);
        }

        ScoreSetter setter = new ScoreSetter("namboy94", "");
        setter.setScore(20, 4);
        */
        ScoreSetter setter = new ScoreSetter("namboy94", "");
        setter.setScore(20, 5);

    }
}
