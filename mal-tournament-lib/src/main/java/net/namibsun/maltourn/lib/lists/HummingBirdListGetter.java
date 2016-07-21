package net.namibsun.maltourn.lib.lists;

import net.namibsun.maltourn.lib.http.HttpHandler;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.objects.HummingBirdAnimeSeries;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that handles fetching of hummingbird.me anime lists
 */
public class HummingBirdListGetter implements ListGetter{

    /**
     * Fetches the anime list of a user
     * @param username the user's username on the specified service
     * @return the anime list as a set of AnimeSeries objects
     * @throws IOException in case the connection failed
     */
    @Override
    public Set<AnimeSeries> getCompletedList(String username) throws IOException {
        Set<AnimeSeries> animeSeries = new HashSet<>();

        String listUrl = "http://hummingbird.me/api/v1/users/" + username + "/library";
        HttpHandler handler = new HttpHandler(listUrl);
        handler.setMethod("GET");
        handler.connect();
        String response = handler.getResponse();
        String[] shows = response.split("}}");

        for (int i = 0; i < shows.length - 1; i++) {
            String show = shows[i] + "}}";
            HummingBirdAnimeSeries series = new HummingBirdAnimeSeries(show);
            if (series.getWatchingStatus().equals("completed")) {
                animeSeries.add(series);
            }
        }
        return animeSeries;
    }
}
