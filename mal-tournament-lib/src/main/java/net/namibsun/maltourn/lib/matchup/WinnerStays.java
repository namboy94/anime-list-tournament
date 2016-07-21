package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.Set;

public class WinnerStays extends SimpleVs {

    private AnimeSeries winner = null;

    public WinnerStays(Set<AnimeSeries> series, String username, String password) {
        super(series, username, password);
    }

}
