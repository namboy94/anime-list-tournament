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

package net.namibsun.maltourn.lib.http;

import org.python.util.PythonInterpreter;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by hermann on 7/9/16.
 */
public class HttpHandler {

    public static String getWithAuth(String target, String authentication) {

        try {
            String response = "";
            URL url = new URL(target);

            String authToken = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authentication.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", authToken);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response += line + "\n";
            }
            reader.close();
            return response;
        } catch (Exception e) {
            return "";
        }
    }

    public static void postWithAuth(String target, String authentication, String payload) {

        try {
            URL url = new URL(target);
            String authToken = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authentication.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", authToken);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            OutputStream out = connection.getOutputStream();

            out.write(payload.getBytes());
            out.flush();
            out.close();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
