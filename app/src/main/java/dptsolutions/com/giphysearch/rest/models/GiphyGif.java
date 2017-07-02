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
     * Undocumented. Probably shortened url direct to the main gif??
     */
    @SerializedName("bitly_gif_url")
    public abstract String bitlyGifUrl();

    /**
     * The unique bit.ly URL for this GIF
     */
    @SerializedName("bitly_url")
    public abstract String bitlyUrl();

    /**
     * A URL used for embedding this GIF
     */
    @SerializedName("embed_url")
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
    @SerializedName("content_url")
    public abstract String contentUrl();

    /**
     * An array of tags for this GIF (Note: Not available when using the Public Beta Key)
     */
    @Nullable
    public abstract List<String> tags();

    /**
     * An array of featured tags for this GIF (Note: Not available when using the Public Beta Key)
     */
    @Nullable
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
    @SerializedName("source_post_url")
    public abstract String sourcePostUrl();

    /**
     * Undocumented. Looks to be a 0 or 1
     */
    @SerializedName("is_indexable")
    public abstract byte isIndexable();

    /**
     * The date on which this GIF was last updated.
     */
    @Nullable
    @SerializedName("update_datetime")
    public abstract LocalDateTime updateDateTime();

    /**
     * The date this GIF was added to the GIPHY database.
     */
    @Nullable
    @SerializedName("create_datetime")
    public abstract LocalDateTime createDateTime();

    /**
     * The creation or upload date from this GIF's source.
     */
    @SerializedName("import_datetime")
    public abstract LocalDateTime importDateTime();

    /**
     * The date on which this gif was marked trending, if applicable.
     * Looks like defaults are either 1/1/1970 or 0/0/0000
     */
    @SerializedName("trending_datetime")
    public abstract LocalDateTime trendingDateTime();

    /**
     * An object containing data for various available formats and sizes of this GIF.
     */
    public abstract GiphyImages images();

    public static TypeAdapter<GiphyGif> typeAdapter(Gson gson) {
        return new AutoValue_GiphyGif.GsonTypeAdapter(gson);
    }
}
