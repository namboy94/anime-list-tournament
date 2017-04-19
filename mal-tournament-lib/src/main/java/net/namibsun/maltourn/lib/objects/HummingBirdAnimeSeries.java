package net.namibsun.maltourn.lib.objects;

import net.namibsun.maltourn.lib.authentication.HummingBirdAuthenticator;
import net.namibsun.maltourn.lib.http.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Class that models a Hummingbird Anime Series
 */
public class HummingBirdAnimeSeries extends AnimeSeries {

    /**
     * The Hummingbird List values/attributes
     */
    private int baseId;
    private int episodesWatched;
    private String lastWatched;
    private String updatedAt;
    private int rewatchedTimes;
    private String notes;
    private boolean areNotesPresent;
    private String watchingStatus;
    private boolean isPrivate;
    private boolean isRewatching;
    private int animeId;
    private int malId;
    private String slug;
    private String showStatus;
    private String url;
    private String title;
    private String alternateTitle;
    private int episodeCount;
    private int episodeLength;
    private String coverImage;
    private String synopsis;
    private String showType;
    private String startedAiring;
    private String finishedAiring;
    private float communityRating;
    private String ageRating;
    private ArrayList<String> genres = new ArrayList<>();
    private float rating;

    /**
     * Constructor that parses a hummingbird JSON element
     * @param jsonData the JSON data fetched from hummingbird.me
     */
    public HummingBirdAnimeSeries(String jsonData) {

        String hummingBirdMetaData = jsonData.split("\\{")[1];
        String showData = jsonData.split(hummingBirdMetaData)[1];
        String[] genres = showData.split("\"genres\":")[1].split("]")[0].split(Pattern.quote("{"));

        this.baseId = this.parseJsonInteger("id", hummingBirdMetaData);
        this.episodesWatched = this.parseJsonInteger("episodes_watched", hummingBirdMetaData);
        this.lastWatched = this.parseJsonString("last_watched", hummingBirdMetaData);
        this.updatedAt = this.parseJsonString("updated_at", hummingBirdMetaData);
        this.rewatchedTimes = this.parseJsonInteger("rewatched_times", hummingBirdMetaData);
        this.notes = this.parseJsonString("notes", hummingBirdMetaData);
        this.areNotesPresent = this.parseJsonBoolean("notes_present", hummingBirdMetaData);
        this.watchingStatus = this.parseJsonString("status", hummingBirdMetaData);
        this.isPrivate = this.parseJsonBoolean("private", hummingBirdMetaData);
        this.isRewatching = this.parseJsonBoolean("rewatching", hummingBirdMetaData);

        this.animeId = this.parseJsonInteger("id", showData);
        this.malId = this.parseJsonInteger("mal_id", showData);
        this.slug = this.parseJsonString("slug", showData);
        this.showStatus = this.parseJsonString("status", showData);
        this.url = this.parseJsonString("url", showData);
        this.title = this.parseJsonString("title", showData);
        this.alternateTitle = this.parseJsonString("alternate_title", showData);
        this.episodeCount = this.parseJsonInteger("episode_count", showData);
        this.episodeLength = this.parseJsonInteger("episode_length", showData);
        this.coverImage = this.parseJsonString("cover_image", showData);
        this.synopsis = this.parseJsonString("synopsis", showData);
        this.showType = this.parseJsonString("show_type", showData);
        this.startedAiring = this.parseJsonString("started_airing", showData);
        this.finishedAiring = this.parseJsonString("finished_airing", showData);
        this.communityRating = this.parseJsonFloat("community_rating", showData, ",");
        this.ageRating = this.parseJsonString("age_rating", showData);
        this.rating = this.parseJsonFloat("value", showData, "\\}");

        boolean first = true;
        for (String genre: genres) {
            if (first) {
                first = false;
            }
            else {
                this.genres.add(this.parseJsonString("name", genre));
            }
        }
    }

    /**
     * Parses a JSON string value
     * @param value the value to get
     * @param jsonData the JSON string
     * @return the JSON String value
     */
    private String parseJsonString(String value, String jsonData) {
        try {
            return jsonData.split("\"" + value + "\":\"")[1].split("\",")[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }
    }

    /**
     * Parses a JSON boolean value
     * @param value the value to get
     * @param jsonData the JSON string
     * @return the JSON boolean value
     */
    private boolean parseJsonBoolean(String value, String jsonData) {
        return Boolean.parseBoolean(jsonData.split("\"" + value + "\":")[1].split(",")[0]);
    }

    /**
     * Parses a JSON int value
     * @param value the value to get
     * @param jsonData the JSON string
     * @return the JSON int value (or -1 if no entry)
     */
    private int parseJsonInteger(String value, String jsonData) {
        try {
            return Integer.parseInt(jsonData.split("\"" + value + "\":")[1].split(",")[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Parses a JSON float value
     * @param value the value to get
     * @param jsonData the JSON string
     * @return the JSON float value (or -1.0f if no entry)
     */
    private float parseJsonFloat(String value, String jsonData, String end) {
        try {
            try {
                return Float.parseFloat(
                        jsonData.split("\"" + value + "\":\"")[1].split("\"" + end)[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                return Float.parseFloat(jsonData.split("\"" + value + "\":")[1].split(end)[0]);
            }
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    /**
     * Sets the score of the series on hummingbird.net
     * @param score the score
     * @param username the user whose score should be change
     * @param password the user's password
     * @throws IOException in case the connection to the server failed
     */
    @Override
    public void setScore(int score, String username, String password) throws IOException {

        this.rating = score;
        String rating = String.format(java.util.Locale.US,"%.1f", ((float)score / 2));

        HttpHandler handler =
                new HttpHandler("http://hummingbird.me/api/v1/libraries/" + this.animeId);
        handler.setMethod("POST");
        handler.setContentType("application/x-www-form-urlencoded");

        String authToken = new HummingBirdAuthenticator().getAuthToken(username, password);
        String payload = "auth_token=" + authToken + "&sane_rating_update=" + rating;

        handler.connect();
        handler.postContent(payload.getBytes());

    }

    /**
     * @return the title of the show
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * @return the current score of the show. Converts Hummingbird-style ratings (0.5 - 5.0) to
     *          MyAnimeList-style scores (1-10)
     */
    @Override
    public int getScore() {
        return Math.round(this.rating * 2);
    }

    /**
     * @return the URL to the cover image
     */
    @Override
    public String getImageUrl() {
        return this.coverImage;
    }

    /**
     * @return the current watching status of the series
     */
    public String getWatchingStatus() {
        return this.watchingStatus;
    }
}
