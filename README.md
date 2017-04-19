# MAL Tournament

This is a program that pits the anime series in a user's myanimelist.net account against each other
in an effort to determine better scores for those series.

This program requires java 8 or higher, install it using your package manager or download it from
[oracle](http://www.oracle.com/technetwork/java/javase/downloads/)

To run the program, download the jar file from the [latest release build](https://github.com/namboy94/mal-tournament/releases),
then run the following command (or double click the jar file on Windows):

    java -jar mal-tournament-java-<version>.jar
    
## Android

The Android Version of the program is published on Google Play.
The App is currently in Beta, which means that it is only available
through [this](https://play.google.com/apps/testing/net.namibsun.maltourn.android) link.

[Store Page](https://play.google.com/store/apps/details?id=net.namibsun.maltourn.android)
    
## How it works

The program will initially ask you for a username and password. Enter your myanimelist.net username and password
there. If the credentials were accepted by myanimelist.net, the program will download your list of completed anime
series. (This may take a while depending on your internet connection, it might look like the program crashed, but don't
worry, it's probably still running). Then you'll see a screen with two series side by side.

To rate these series, select which series you like more (or click on the draw button if you can't decide). If the
decision is in line with your current scores, the program will jump to the next matchup.

If the decision did not match your previous scores, the text fields below the show images are filled with the
current scores of the images, which you can then edit to your liking.

Afterwards, you can press either the Cancel button to keep your scores as they were or the Confirm button to
set the scores to the ones currently entered in the text fields.

Rinse and Repeat

## Further Information

* [Changelog](https://gitlab.namibsun.net/namboy94/anime-list-tournament/raw/master/CHANGELOG)
* [Gitlab](https://gitlab.namibsun.net/namboy94/anime-list-tournament)
* [Github](https://github.com/namboy94/anime-list-tournament)
* [Play Store Entry](https://play.google.com/store/apps/details?id=net.namibsun.maltourn.android)
* [Git Statistics (gitstats)](https://gitstats.namibsun.net/gitstats/anime-list-tournament/index.html)
* [Git Statistics (git_stats)](https://gitstats.namibsun.net/git_stats/anime-list-tournament/index.html)
* [Privacy Statement](maltourn-android/resources/playstore/privacy_statement.txt)
