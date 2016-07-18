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
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import net.iharder.Base64;
import net.namibsun.maltourn.android.R;
import net.namibsun.maltourn.lib.gets.Authenticator;
import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Activity that handles the login of the user to myanimelist.net
 */
public class LoginActivity extends AnalyticsActivity {

    private String username;
    private String password;
    private ProgressBar loginProgressCircle;

    /**
     * Creates the login activity
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    protected void onCreate(Bundle savedInstanceState) {

        //Remove this!
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // this.analyticsActive = false;
        this.layoutFile = R.layout.activity_login;
        this.screenName = "Login";
        this.analyticsName = "MAL-Login";
        super.onCreate(savedInstanceState);

        this.loginProgressCircle = (ProgressBar) this.findViewById(R.id.login_loader);
        //this.loginProgressCircle.setVisibility(View.INVISIBLE);
        Button loginButton = (Button) this.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.getLoginData();
                // Network on main thread exceptions are bad!

                if (Authenticator.isAuthenticated(LoginActivity.this.username, LoginActivity.this.password)) {
                    LoginActivity.this.loginProgressCircle.setVisibility(View.VISIBLE);
                    for (AnimeSeries serie: ListGetter.getList(LoginActivity.this.username)) {
                        Log.e("Anime", serie.seriesTitle);
                    }
                    LoginActivity.this.loginProgressCircle.setVisibility(View.INVISIBLE);
                } else {
                    LoginActivity.this.showAuthenticationErrorDialog();
                }
            }
        });
    }

    private void getLoginData() {
        EditText usernameEditText = (EditText) this.findViewById(R.id.usernameEntry);
        this.username = usernameEditText.getText().toString();
        EditText passwordEditText = (EditText) this.findViewById(R.id.passwordEntry);
        this.password = passwordEditText.getText().toString();
        Log.e("Test", this.username);
        Log.e("Test", this.password);
    }

    private void showAuthenticationErrorDialog(){
        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(this);
        errorDialogBuilder.setTitle("Authentication Error");
        errorDialogBuilder.setMessage("Wrong username/password");
        errorDialogBuilder.setCancelable(true);
        errorDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        errorDialogBuilder.create();
        errorDialogBuilder.show();
    }
}
