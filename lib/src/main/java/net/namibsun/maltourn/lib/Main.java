package net.namibsun.maltourn.lib;

import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by hermann on 7/9/16.
 */
public class Main {

    public static void main(String[] args) {

        //was 5
        /*
        ListGetter list = new ListGetter("namboy94", "");
        ArrayList<AnimeSeries> series = list.getList();
        for (AnimeSeries serie: series) {
            System.out.println(serie.myScore);
        }

        ScoreSetter setter = new ScoreSetter("namboy94", "");
        setter.setScore(20, 4);
        */
        ScoreSetter setter = new ScoreSetter("namboy94", "");
        setter.setScore(20, 5);

    }
}
