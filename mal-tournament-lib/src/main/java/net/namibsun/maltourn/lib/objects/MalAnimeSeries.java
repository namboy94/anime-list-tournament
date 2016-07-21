package net.namibsun.maltourn.lib.objects;

import net.namibsun.maltourn.lib.http.HttpHandler;

import java.io.IOException;

/**
 * Class that models an anime series from myanimelist.net
 */
public class MalAnimeSeries extends AnimeSeries{

    /**
     * The data from the XML returned by myanimelist.net
     */
    private String xmlData;
    private int seriesAnimedbId;
    private String seriesTitle;
    private String seriesSynonyms;
    private int seriesType;
    private int seriesEpisodes;
    private int seriesStatus;
    private String seriesStart;
    private String seriesEnd;
    private String seriesImage;
    private int myId;
    private int myWatchedEpisodes;
    private String myStartDate;
    private String myFinishDate;
    private int myScore;
    private int myStatus;
    private int myRewatching;
    private int myRewatchingEp;
    private int myLastUpdated;
    private String myTags;

    /**
     *  The Constructor takes an XML data string and parses it to get all the variable data
     * @param xmlData the XML data string to parse
     */
    public MalAnimeSeries(String xmlData) {
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
     * Sets the score of the series on myanimelist.net
     * @param score the score
     * @param username the user whose score should be change
     * @param password the user's password
     * @throws IOException in case the connection fails
     */
    public void setScore(int score, String username, String password) throws IOException {
        String payload = "data=%3Centry%3E%3Cscore%3E" + score + "%3C%2Fscore%3E%3C%2Fentry%3E";
        String scoreSetUrl = "http://myanimelist.net/api/animelist/update/" + this.seriesAnimedbId + ".xml";

        HttpHandler handler = new HttpHandler(scoreSetUrl);
        handler.setBasicAuthentication(username, password);
        handler.setMethod("POST");
        handler.setContentType("application/x-www-form-urlencoded");
        handler.connect();
        handler.postContent(payload.getBytes());
    }

    /**
     * @return the series' title
     */
    @Override
    public String getTitle() {
        return this.seriesTitle;
    }

    /**
     * @return the series' score
     */
    @Override
    public int getScore() {
        return this.myScore;
    }

    /**
     * @return the series' image URL
     */
    @Override
    public String getImageUrl() {
        return this.seriesImage;
    }

}
