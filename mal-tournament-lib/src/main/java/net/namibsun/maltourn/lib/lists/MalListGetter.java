package net.namibsun.maltourn.lib.lists;

import net.namibsun.maltourn.lib.http.HttpHandler;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.objects.MalAnimeSeries;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that handles fetching of myanimelist.net anime lists
 */
public class MalListGetter implements ListGetter{

    /**
     * Fetches the Anime list from myanimelist.net
     * @param username the user's username on the specified service
     * @return the anime list as a set of AnimeSeries objects
     * @throws IOException in case the connection failed
     */
    @Override
    public Set<AnimeSeries> getCompletedList(String username) throws IOException {
        Set<AnimeSeries> series = new HashSet<>();

        HttpHandler handler = new HttpHandler("http://myanimelist.net/malappinfo.php");
        handler.setUrlParameter("status", "all");
        handler.setUrlParameter("type", "anime");
        handler.setUrlParameter("u", username);
        handler.setMethod("GET");
        handler.connect();
        String response = handler.getResponse();

        for (String xmlData: response.split("<anime>")) {
            if (xmlData.contains("<my_status>2</my_status>")) {                //Status 2 = Completed
                series.add(new MalAnimeSeries(xmlData.split("</anime>")[0]));  //Get XML data from inside <anime> tags
            }
        }

        return series;
    }
}
