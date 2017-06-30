package dptsolutions.com.giphysearch.rest.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

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
    public abstract GiphyAllFormatsImage fixedHeight();

    /**
     * Data surrounding a static image of this GIF with a fixed height of 200 pixels.
     */
    public abstract GiphyStillImage fixedHeightStill();

    /**
     * Data surrounding versions of this GIF with a fixed height of 200 pixels and the number of frames reduced to 6.
     */
    public abstract GiphyWebPImage fixedHeightDownsampled();

    /**
     * Data surrounding versions of this GIF with a fixed width of 200 pixels. Good for mobile use.
     */
    public abstract GiphyAllFormatsImage fixedWidth();

    /**
     * Data surrounding a static image of this GIF with a fixed height of 200 pixels.
     */
    public abstract GiphyStillImage fixedWidthStill();

    /**
     * Data surrounding versions of this GIF with a fixed width of 200 pixels and the number of frames reduced to 6.
     */
    public abstract GiphyWebPImage fixedWidthDownsampled();

    /**
     * Data surrounding versions of this GIF with a fixed height of 100 pixels. Good for mobile keyboards.
     */
    public abstract GiphyAllFormatsImage fixedHeightSmall();

    /**
     * Data surrounding a static image of this GIF with a fixed height of 100 pixels.
     */
    public abstract GiphyStillImage fixedHeightSmallStill();

    /**
     * Data surrounding versions of this GIF with a fixed width of 100 pixels. Good for mobile keyboards.
     */
    public abstract GiphyAllFormatsImage fixedWidthSmall();

    /**
     * Data surrounding a static image of this GIF with a fixed width of 100 pixels.
     */
    public abstract GiphyStillImage fixedWidthSmallStill();

    /**
     * Data surrounding a version of this GIF downsized to be under 2mb.
     */
    public abstract GiphyUnspecifiedImage downsized();

    /**
     * Data surrounding a static preview image of the downsized version of this GIF.
     */
    public abstract GiphyStillImage downsizedStill();

    /**
     * Data surrounding a version of this GIF downsized to be under 8mb.
     */
    public abstract GiphyUnspecifiedImage downsizedLarge();

    /**
     * Data surrounding a version of this GIF downsized to be under 5mb.
     */
    public abstract GiphyUnspecifiedImage downsizedMedium();

    /**
     * Data surrounding a version of this GIF downsized to be under 200kb.
     */
    public abstract GiphyMp4OnlyImage downsizedSmall();

    /**
     * Data surrounding the original version of this GIF. Good for desktop use.
     */
    public abstract GiphyAllFormatsImage original();

    /**
     * Data surrounding a static preview image of the original GIF.
     */
    public abstract GiphyStillImage originalStill();

    /**
     * Undocumented. Looks like its the original gif in MP4 format
     */
    public abstract GiphyMp4OnlyImage originalMp4();

    /**
     * Data surrounding a version of this GIF set to loop for 15 seconds.
     */
    public abstract GiphyLoopingImage looping();

    /**
     * Data surrounding a version of this GIF in .MP4 format limited to 50kb that displays the first 1-2 seconds of the GIF.
     */
    public abstract GiphyMp4OnlyImage preview();

    /**
     * Data surrounding a version of this GIF limited to 50kb that displays the first 1-2 seconds of the GIF.
     */
    public abstract GiphyUnspecifiedImage previewGif();

    /**
     * Undocumented. Looks like it serves up a 50kb webp format of the GIF
     */
    public abstract GiphyUnspecifiedImage previewWebp();


    public static TypeAdapter<GiphyImages> typeAdapter(Gson gson) {
        return new AutoValue_GiphyImages.GsonTypeAdapter(gson);
    }
}
