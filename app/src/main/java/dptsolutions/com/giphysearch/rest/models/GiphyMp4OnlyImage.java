package dptsolutions.com.giphysearch.rest.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * A GIF that has only an .mp4 version
 */
@AutoValue
public abstract class GiphyMp4OnlyImage implements GiphyImageMp4 {

    /**
     * The width of this GIF in pixels.
     */
    public abstract String width();

    /**
     * The height of this GIF in pixels.
     */
    public abstract String height();

    public static TypeAdapter<GiphyMp4OnlyImage> typeAdapter(Gson gson) {
        return new AutoValue_GiphyMp4OnlyImage.GsonTypeAdapter(gson);
    }
}
