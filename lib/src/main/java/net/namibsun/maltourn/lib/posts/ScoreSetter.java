package net.namibsun.maltourn.lib.posts;

import net.namibsun.maltourn.lib.http.HttpHandler;

/**
 * Created by hermann on 7/10/16.
 */
public class ScoreSetter {

    private String authentication;
    private String username;

    public ScoreSetter(String username, String password) {
        this.authentication = username + ":" + password;
        this.username = username;
    }

    public void setScore(int seriesAnimedbId, int score) {
        String url = "http://myanimelist.net/api/animelist/update/" + seriesAnimedbId + ".xml";
        String payload = "<my_score>" + score + "</my_score>";
        HttpHandler.postWithAuth(url, authentication, payload);
    }

}
