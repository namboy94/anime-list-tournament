package net.namibsun.maltourn.lib;

import java.io.*;
import java.net.*;

/**
 * Created by hermann on 7/9/16.
 */
public class Main {

    public static void main(String[] args) {

        try {
            System.out.print(getHTML("http://myanimelist.net"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static String getHTML(String websiteUrl) throws Exception {
        //StringBuilder result = new StringBuilder();

        String content = "";
        URL url = new URL(websiteUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }
        reader.close();
        return content;
    }

}
