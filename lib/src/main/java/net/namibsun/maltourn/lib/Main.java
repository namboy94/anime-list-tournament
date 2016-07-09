package net.namibsun.maltourn.lib;

import net.namibsun.maltourn.lib.gets.ListGetter;

import java.io.*;
import java.net.*;

/**
 * Created by hermann on 7/9/16.
 */
public class Main {

    public static void main(String[] args) {

        ListGetter list = new ListGetter("namboy94", "");
        System.out.println(list.getList());
    }
}
