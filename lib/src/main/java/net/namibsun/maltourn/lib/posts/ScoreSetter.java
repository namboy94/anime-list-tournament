package net.namibsun.maltourn.lib.posts;

import net.namibsun.maltourn.lib.http.HttpHandler;

/**
 * Created by hermann on 7/10/16.
 */
public class ScoreSetter {

    private String authentication;

    public ScoreSetter(String username, String password) {
        this.authentication = username + ":" + password;
    }

    public void setScore(int seriesAnimedbId, int score) {
        String url = "http://myanimelist.net/api/animelist/update/" + seriesAnimedbId + ".xml";
        String payload = "data=%3Centry%3E%3Cscore%3E" + score + "%3C%2Fscore%3E%3C%2Fentry%3E";
        HttpHandler.postWithAuth(url, authentication, payload);
    }

}
