package dptsolutions.com.giphysearch.rest.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * A GIF that has only a looping .mp4
 */
@AutoValue
public abstract class GiphyLoopingImage implements GiphyImageMp4 {

    /**
     * The width of this GIF in pixels.
     */
    public abstract String width();

    /**
     * The height of this GIF in pixels.
     */
    public abstract String height();

    public static TypeAdapter<GiphyLoopingImage> typeAdapter(Gson gson) {
        return new AutoValue_GiphyLoopingImage.GsonTypeAdapter(gson);
    }
}
