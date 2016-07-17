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

package net.namibsun.maltourn.java.objects;

/**
 * Class that models a myanimelist.net Anime Series
 */
@SuppressWarnings("WeakerAccess")
public class AnimeSeries {

    public String xmlData;
    public int seriesAnimedbId;
    public String seriesTitle;
    public String seriesSynonyms;
    public int seriesType;
    public int seriesEpisodes;
    public int seriesStatus;
    public String seriesStart;
    public String seriesEnd;
    public String seriesImage;
    public int myId;
    public int myWatchedEpisodes;
    public String myStartDate;
    public String myFinishDate;
    public int myScore;
    public int myStatus;
    public int myRewatching;
    public int myRewatchingEp;
    public int myLastUpdated;
    public String myTags;

    /**
     * The Constructor takes an XML data string and parses it to get all the variable data
     * @param xmlData the XML data string to parse
     */
    public AnimeSeries(String xmlData) {
        this.xmlData = xmlData;
        this.seriesAnimedbId = this.convertToInt(this.getData("series_animedb_id"));
        this.seriesTitle = this.getData("series_title");
        this.seriesSynonyms = this.getData("series_synonyms");
        this.seriesType = this.convertToInt(this.getData("series_type"));
        this.seriesEpisodes = this.convertToInt(this.getData("series_episodes"));
        this.seriesStatus = this.convertToInt(this.getData("series_status"));
        this.seriesStart = this.getData("series_start");
        this.seriesEnd = this.getData("series_end");
        this.seriesImage = this.getData("series_image");
        this.myId = this.convertToInt(this.getData("my_id"));
        this.myWatchedEpisodes = this.convertToInt(this.getData("my_watched_episodes"));
        this.myStartDate = this.getData("my_start_date");
        this.myFinishDate = this.getData("my_finish_date");
        this.myScore = this.convertToInt(this.getData("my_score"));
        this.myStatus = this.convertToInt(this.getData("my_status"));
        this.myRewatching = this.convertToInt(this.getData("my_rewatching"));
        this.myRewatchingEp = this.convertToInt(this.getData("my_rewatching_ep"));
        this.myLastUpdated = this.convertToInt(this.getData("my_last_updated"));

        try {
            this.myTags = this.getData("my_tags");
        } catch (ArrayIndexOutOfBoundsException e) {
            this.myTags = "";
        }

    }

    /**
     * Gets the data from inside an XML tag
     * @param dataType the XML tag
     * @return the data from inside the XML tag
     */
    private String getData(String dataType) {
        return xmlData.split("<" + dataType + ">")[1].split("</" + dataType + ">")[0];
    }

    /**
     * Converts a String to an int, and if the String is not parseable, the result is -1
     * @param number the String to parse
     * @return the parsed integer value
     */
    private int convertToInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
