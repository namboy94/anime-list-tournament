package net.namibsun.maltourn.lib.lists;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.io.IOException;
import java.util.Set;

/**
 * Interface that defines how to check for anime list data for a user
 */
public interface ListGetter {

    /**
     * Gets the completed anime list of a user
     * @param username the user's username on the specified service
     * @return a set of AnimeSeries.
     * @throws IOException when a network error occured
     */
    Set<AnimeSeries> getCompletedList(String username) throws IOException;

}
