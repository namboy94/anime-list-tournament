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

        PythonInterpreter inter = new PythonInterpreter();
        String authToken = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authentication.getBytes());

        String pythonCode =
                "import urllib, urllib2\n" +
                        "auth = '" + authToken + "'\n" +
                        "opener = urllib2.build_opener()\n" +
                        "opener.addheaders = [('User-Agent', 'api-team-f894427cc1c571f79da49605ef8b112f'), " +
                        "('Authorization', auth)]\n" +
                        "data = '" + payload + "'\n" +
                        "opener.open('" + target + "', data)\n";

        inter.exec(pythonCode);

        /*
        try {
            URL url = new URL(target);
            String authToken = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authentication.getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", authToken);
            connection.setRequestProperty("Content-Type", "text/xml");


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
        }*/
    }

}
