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

import net.iharder.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class that handles Http Connections
 */
public class HttpHandler {

    /**
     * The URL to use when communicating over HTTP
     */
    private String url;

    /**
     * The Http Connection
     */
    private HttpURLConnection connection;

    /**
     * Flag that is set whenever the connection has been established
     */
    private boolean isConnected = false;

    /**
     * Used to check if a ? or & has to be prepended to a new URL parameter
     */
    private boolean alreadyHasParameter = false;

    /**
     * List of Runnables that will be run after the connection is initialized
     */
    private ArrayList<Runnable> connectionOptions = new ArrayList<>();

    /**
     * A string detailing all connection errors. Will stay empty if not errors occur
     */
    private String connectionErrors = "";

    /**
     * Constructor that takes a URL string as parameter and stores it
     * @param url the URL string
     */
    public HttpHandler(String url) {
        this.url = url;
    }

    /**
     * Connects the HTTP connection
     * @throws IOException in case the connection fails
     */
    public void connect() throws IOException{
        URL url = new URL(this.url);
        this.connection = (HttpURLConnection) url.openConnection();
        for (Runnable option: this.connectionOptions) {
            option.run();
        }
        if (!this.connectionErrors.equals("")) {
            throw new IOException(this.connectionErrors);
        }
        this.isConnected = true;
    }

    /**
     * Sets a basic authentication header
     * @param username the username to be used
     * @param password the password to be used
     */
    public void setBasicAuthentication(final String username, final String password) {
        this.addConnectionOption(new Runnable() {
            @Override
            public void run() {
                String authentication = username + ":" + password;
                String authToken = "Basic " + Base64.encodeBytes(authentication.getBytes());
                HttpHandler.this.connection.setRequestProperty("Authorization", authToken);
            }
        });
    }

    /**
     * Sets the content type of the request
     * @param contentType the content type
     */
    public void setContentType(final String contentType) {
        this.addConnectionOption(new Runnable() {
            @Override
            public void run() {
                HttpHandler.this.connection.setRequestProperty("Content-Type", contentType);
            }
        });
    }

    /**
     * Sets the HTTP request method (commonly POST or GET)
     * @param method the method used
     */
    public void setMethod(final String method){
        this.addConnectionOption(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpHandler.this.connection.setRequestMethod(method);
                    if (method.equals("POST")) {
                        HttpHandler.this.connection.setDoOutput(true);
                    }
                } catch (ProtocolException e) {
                    HttpHandler.this.addError(e);
                }
            }
        });
    }

    /**
     * Adds a new URL parameter to the URL
     * @param parameterName the name of the parameter
     * @param parameterValue the value of the parameter
     */
    public void setUrlParameter(String parameterName, String parameterValue) {
        String parameter = parameterName + "=" + parameterValue;
        if (this.alreadyHasParameter) {
            this.url += "&" + parameter;
        }
        else {
            this.url += "?" + parameter;
            this.alreadyHasParameter = true;
        }
    }

    /**
     * Adds an error to the connection error string
     * @param error the error that occured
     */
    private void addError(Exception error) {
        this.connectionErrors += error.toString() + "\nENDOFERROR\n";
    }

    /**
     * Sets a new connection option.
     * If the connection has bee established already, the option will be set automatically,
     * otherwise it will be stored in the connection options array list and set once the connection
     * has been established
     * @param option the runnable option to set
     */
    private void addConnectionOption(Runnable option) {
        if (this.isConnected) {
            option.run();
        }
        else {
            this.connectionOptions.add(option);
        }
    }

    /**
     * Reads the Server Response to the HTTP request and turns it into a string
     * @return the received string
     */
    public String getResponse() {

        String response = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
            this.addError(e);
        }
        return response;
    }

    /**
     * Writes bytes to the connection
     * @param payload the bytes to send.
     *                <joke>Only send the best bytes, otherwise the server won't be happy</joke>
     */
    public void postContent(byte[] payload) {
        try {
            OutputStream out = connection.getOutputStream();
            out.write(payload);
            out.flush();
            out.close();
        } catch (IOException e) {
            this.addError(e);
        }
        System.out.println(this.getResponse());
    }
}
