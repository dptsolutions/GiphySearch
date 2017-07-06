package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Giphy image metadata object
 *
 * @see <a href="https://developers.giphy.com/docs/#/definitions/Images">https://developers.giphy.com/docs/#/definitions/Images</a>
 */
@AutoValue
public abstract class GiphyImages {

    /**
     * Data surrounding versions of this GIF with a fixed height of 200 pixels. Good for mobile use.
     */
    @Nullable
    @SerializedName("fixed_height")
    public abstract GiphyAllFormatsImage fixedHeight();

    /**
     * Data surrounding a static image of this GIF with a fixed height of 200 pixels.
     */
    @Nullable
    @SerializedName("fixed_height_still")
    public abstract GiphyStillImage fixedHeightStill();

    /**
     * Data surrounding versions of this GIF with a fixed height of 200 pixels and the number of frames reduced to 6.
     */
    @Nullable
    @SerializedName("fixed_height_downsampled")
    public abstract GiphyWebPImage fixedHeightDownsampled();

    /**
     * Data surrounding versions of this GIF with a fixed width of 200 pixels. Good for mobile use.
     */
    @Nullable
    @SerializedName("fixed_width")
    public abstract GiphyAllFormatsImage fixedWidth();

    /**
     * Data surrounding a static image of this GIF with a fixed height of 200 pixels.
     */
    @Nullable
    @SerializedName("fixed_width_still")
    public abstract GiphyStillImage fixedWidthStill();

    /**
     * Data surrounding versions of this GIF with a fixed width of 200 pixels and the number of frames reduced to 6.
     */
    @Nullable
    @SerializedName("fixed_width_downsampled")
    public abstract GiphyWebPImage fixedWidthDownsampled();

    /**
     * Data surrounding versions of this GIF with a fixed height of 100 pixels. Good for mobile keyboards.
     */
    @Nullable
    @SerializedName("fixed_height_small")
    public abstract GiphyAllFormatsImage fixedHeightSmall();

    /**
     * Data surrounding a static image of this GIF with a fixed height of 100 pixels.
     */
    @Nullable
    @SerializedName("fixed_height_small_still")
    public abstract GiphyStillImage fixedHeightSmallStill();

    /**
     * Data surrounding versions of this GIF with a fixed width of 100 pixels. Good for mobile keyboards.
     */
    @Nullable
    @SerializedName("fixed_width_small")
    public abstract GiphyAllFormatsImage fixedWidthSmall();

    /**
     * Data surrounding a static image of this GIF with a fixed width of 100 pixels.
     */
    @Nullable
    @SerializedName("fixed_width_small_still")
    public abstract GiphyStillImage fixedWidthSmallStill();

    /**
     * Data surrounding a version of this GIF downsized to be under 2mb.
     */
    @Nullable
    public abstract GiphyUnspecifiedImage downsized();

    /**
     * Data surrounding a static preview image of the downsized version of this GIF.
     */
    @Nullable
    @SerializedName("downsized_still")
    public abstract GiphyStillImage downsizedStill();

    /**
     * Data surrounding a version of this GIF downsized to be under 8mb.
     */
    @Nullable
    @SerializedName("downsized_large")
    public abstract GiphyUnspecifiedImage downsizedLarge();

    /**
     * Data surrounding a version of this GIF downsized to be under 5mb.
     */
    @Nullable
    @SerializedName("downsized_medium")
    public abstract GiphyUnspecifiedImage downsizedMedium();

    /**
     * Data surrounding a version of this GIF downsized to be under 200kb.
     */
    @Nullable
    @SerializedName("downsized_small")
    public abstract GiphyMp4OnlyImage downsizedSmall();

    /**
     * Data surrounding the original version of this GIF. Good for desktop use.
     */
    public abstract GiphyAllFormatsImage original();

    /**
     * Data surrounding a static preview image of the original GIF.
     */
    @Nullable
    @SerializedName("original_still")
    public abstract GiphyStillImage originalStill();

    /**
     * Undocumented. Looks like its the original gif in MP4 format
     */
    @Nullable
    @SerializedName("original_mp4")
    public abstract GiphyMp4OnlyImage originalMp4();

    /**
     * Data surrounding a version of this GIF set to loop for 15 seconds.
     */
    @Nullable
    public abstract GiphyLoopingImage looping();

    /**
     * Data surrounding a version of this GIF in .MP4 format limited to 50kb that displays the first 1-2 seconds of the GIF.
     */
    @Nullable
    public abstract GiphyMp4OnlyImage preview();

    /**
     * Data surrounding a version of this GIF limited to 50kb that displays the first 1-2 seconds of the GIF.
     */
    @Nullable
    @SerializedName("preview_gif")
    public abstract GiphyUnspecifiedImage previewGif();

    /**
     * Undocumented. Looks like it serves up a 50kb webp format of the GIF
     */
    @Nullable
    @SerializedName("preview_webp")
    public abstract GiphyUnspecifiedImage previewWebp();


    public static TypeAdapter<GiphyImages> typeAdapter(Gson gson) {
        return new AutoValue_GiphyImages.GsonTypeAdapter(gson);
    }
}
