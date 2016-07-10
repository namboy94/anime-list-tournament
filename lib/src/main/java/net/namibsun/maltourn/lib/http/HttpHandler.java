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
