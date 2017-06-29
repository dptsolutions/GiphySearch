package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * A GIF that has all formats (.gif, .mp4, .webp)
 */
@AutoValue
public abstract class GiphyAllFormatsImage implements GiphyImageBase, GiphyImageMp4, GiphyImageWebP {

    /**
     * The size of this GIF in bytes.
     */
    public abstract String size();

    /**
     * The number of frames in this GIF.
     */
    @Nullable
    public abstract String frames();

    public static TypeAdapter<GiphyAllFormatsImage> typeAdapter(Gson gson) {
        return new AutoValue_GiphyAllFormatsImage.GsonTypeAdapter(gson);
    }
}
