package net.namibsun.maltourn.lib.lists;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.io.IOException;
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
        return null;
    }
}
