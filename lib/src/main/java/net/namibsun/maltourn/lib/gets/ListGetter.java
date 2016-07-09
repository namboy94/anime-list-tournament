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
