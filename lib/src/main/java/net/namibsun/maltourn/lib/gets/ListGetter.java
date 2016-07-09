package net.namibsun.maltourn.lib.gets;

import net.namibsun.maltourn.lib.http.HttpHandler;

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

    public String getList() {
        String list =  HttpHandler.getWithAuth("http://myanimelist.net/malappinfo.php?status=all&type=anime&u=" + this.user, this.authentication);
        String result = "";
        for (String test: list.split("<anime>")) {
            //statuscode 1 = watching, 2=completed, 3=onhold, 4=dropped
            if (test.contains("<my_status>2</my_status>")) {
                result += test.split("</anime>")[0] + "\n";
            }
        }
        return result;
    }

}
