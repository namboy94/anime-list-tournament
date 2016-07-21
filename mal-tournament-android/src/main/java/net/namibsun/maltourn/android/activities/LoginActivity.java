/*
Copyright 2016 Hermann Krumrey

This file is part of mal-tournament.

    mal-tournament is a program that lets a user pit his watched anime series
    from myanimelist.net against each other in an attempt to determine relative scores
    between the shows.

    mal-tournament is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    mal-tournament is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with mal-tournament. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.maltourn.android.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.content.DialogInterface;
import android.widget.Spinner;
import net.namibsun.maltourn.android.R;
import net.namibsun.maltourn.lib.authentication.HummingBirdAuthenticator;
import net.namibsun.maltourn.lib.authentication.MalAuthenticator;

import java.io.IOException;

/**
 * Activity that handles the login of the user to myanimelist.net
 */
public class LoginActivity extends AnalyticsActivity {

    /**
     * The myanimelist.net username of the user
     */
    private String username;

    /**
     * The myanimelist.net password of the user
     */
    private String password;

    /**
     * The currently selected anime list service
     */
    private String selectedService;

    /**
     * Creates the login activity and sets the login button
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    protected void onCreate(Bundle savedInstanceState) {

        this.layoutFile = R.layout.activity_login;
        this.screenName = "Login";
        this.analyticsName = "Login";
        super.onCreate(savedInstanceState);

        Button loginButton = (Button) this.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Reads the user input username/password and then tries to log in
             * @param v the login button
             */
            @Override
            public void onClick(View v) {
                LoginActivity.this.getLoginData();
                new AsyncLogin().execute();
            }
        });

        final Spinner serviceSelector = (Spinner) LoginActivity.this.findViewById(R.id.serviceSelector);
        serviceSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoginActivity.this.selectedService = serviceSelector.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LoginActivity.this.selectedService = serviceSelector.getSelectedItem().toString();
            }
        });
    }

    /**
     * Fetches the username and password from the text entries and stores the values
     * in their dedicated class variables
     */
    private void getLoginData() {
        EditText usernameEditText = (EditText) this.findViewById(R.id.usernameEntry);
        this.username = usernameEditText.getText().toString();
        EditText passwordEditText = (EditText) this.findViewById(R.id.passwordEntry);
        this.password = passwordEditText.getText().toString();
        Log.e("Test", this.username);
        Log.e("Test", this.password);
    }

    /**
     * Starts the overview activity and gives it the username and password
     */
    private void startOverViewActivity() {
        Intent overViewActivity = new Intent(this, OverViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", this.username);
        bundle.putString("password", this.password);
        bundle.putString("service", this.selectedService);
        overViewActivity.putExtras(bundle);
        this.startActivity(overViewActivity);
    }

    /**
     * Checks if the entered username and password are accepted by myanimelist.net.
     * If it is successful, the overview activity is started, else an authentication
     * error dialog is shown
     */
    private class AsyncLogin extends AsyncTask<Void, Void, Void> {

        /**
         * Runs the authentication check in a separate background thread
         * @param params nothing
         * @return nothing
         */
        protected Void doInBackground(Void... params) {

            try {
                boolean authenticated = false;
                if (LoginActivity.this.selectedService.equals("MyAnimeList")) {
                    authenticated = new MalAuthenticator().isAuthenticated(
                            LoginActivity.this.username, LoginActivity.this.password);
                }
                else if (LoginActivity.this.selectedService.equals("Hummingbird")) {
                    authenticated = new HummingBirdAuthenticator().isAuthenticated(
                            LoginActivity.this.username, LoginActivity.this.password);
                }

                if (authenticated) {
                    LoginActivity.this.startOverViewActivity();
                } else {
                    runOnUiThread(new Runnable() {
                        /**
                         * Shows the user an authentication error dialog because the
                         * credentials were invalid
                         */
                        @Override
                        public void run() {
                            LoginActivity.this.showErrorDialog("Authentication Error", "Wrong username/password");
                        }
                    });
                }
            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    /**
                     * Shows the user an authentication error dialog because the
                     * credentials were invalid
                     */
                    @Override
                    public void run() {
                        LoginActivity.this.showErrorDialog("Connection Error", "Connection to Server failed");
                    }
                });
            }
            return null;
        }
    }
}
