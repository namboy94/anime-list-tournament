package net.namibsun.maltourn.lib.cli;

import java.io.Console;
import java.util.ArrayList;

import net.namibsun.maltourn.lib.gets.Authenticator;

/**
 * Created by hermann on 7/12/16.
 */
public class CliMalTournament {

    Console console = System.console();
    String username;
    String password;

    public CliMalTournament() {

        System.out.println("Please enter your username");
        this.username = this.console.readLine();
        System.out.println("Please enter your password");
        this.password = this.console.readLine();

        if (!Authenticator.isAuthenticated(this.username, this.password)) {
            System.out.println("Invalid username/password");
            System.exit(1);
        }

    }


}
