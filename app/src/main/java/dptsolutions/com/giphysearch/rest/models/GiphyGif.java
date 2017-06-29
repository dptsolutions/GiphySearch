package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDateTime;

import java.util.List;

/**
 * Giphy Gif data object
 */
@AutoValue
public abstract class GiphyGif {

    /**
     * By default, this is almost always gif
     */
    public abstract String type();

    /**
     * This GIF's unique ID
     */
    public abstract String id();

    /**
     * The unique slug used in this GIF's URL
     */
    public abstract String slug();

    /**
     * The unique URL for this GIF
     */
    public abstract String url();

    /**
     * The unique bit.ly URL for this GIF
     */
    public abstract String bitlyUrl();

    /**
     * A URL used for embedding this GIF
     */
    public abstract String embedUrl();

    /**
     * The username this GIF is attached to, if applicable
     */
    public abstract String username();

    /**
     * The page on which this GIF was found
     */
    public abstract String source();

    /**
     * The MPAA-style rating for this content. Examples include Y, G, PG, PG-13 and R
     */
    public abstract String rating();

    /**
     * Currently unused
     */
    public abstract String contentUrl();

    /**
     * An array of tags for this GIF (Note: Not available when using the Public Beta Key)
     */
    public abstract List<String> tags();

    /**
     * An array of featured tags for this GIF (Note: Not available when using the Public Beta Key)
     */
    public abstract List<String> featuredTags();

    /**
     * An object containing data about the user associated with this GIF, if applicable.
     */
    @Nullable
    public abstract GiphyUser user();

    /**
     * The top level domain of the source URL.
     */
    @SerializedName("source_tld")
    public abstract String sourceTLD();

    /**
     * The URL of the webpage on which this GIF was found.
     */
    public abstract String sourcePostUrl();

    /**
     * The date on which this GIF was last updated.
     */
    @SerializedName("update_datetime")
    public abstract LocalDateTime updateDateTime();

    /**
     * The date this GIF was added to the GIPHY database.
     */
    @SerializedName("create_datetime")
    public abstract LocalDateTime createDateTime();

    /**
     * The date on which this gif was marked trending, if applicable.
     */
    @SerializedName("trending_datetime")
    @Nullable
    public abstract LocalDateTime trendingDateTime();

    /**
     * An object containing data for various available formats and sizes of this GIF.
     */
    public abstract GiphyImages images();

    public static TypeAdapter<GiphyGif> typeAdapter(Gson gson) {
        return new AutoValue_GiphyGif.GsonTypeAdapter(gson);
    }
}
