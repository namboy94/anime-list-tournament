package net.namibsun.maltourn.lib.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

}
