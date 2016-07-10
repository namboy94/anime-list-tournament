package net.namibsun.maltourn.lib.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hermann on 7/9/16.
 */
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

    private String getData(String dataType) {
        return xmlData.split("<" + dataType + ">")[1].split("</" + dataType + ">")[0];
    }
    
    private int convertToInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
