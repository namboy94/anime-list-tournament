package net.namibsun.maltourn.lib;

import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by hermann on 7/9/16.
 */
public class Main {

    public static void main(String[] args) {

        ListGetter list = new ListGetter("namboy94", "");
        ArrayList<AnimeSeries> series = list.getList();
        for (AnimeSeries serie: series) {
            System.out.println(serie.seriesTitle);
        }
    }
}
