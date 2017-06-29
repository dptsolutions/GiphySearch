package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * A GIF that has only a .gif version
 */
@AutoValue
public abstract class GiphyGifOnlyImage implements GiphyImageBase {

    /**
     * The size of this GIF in bytes.
     */
    public abstract String size();

    public static TypeAdapter<GiphyGifOnlyImage> typeAdapter(Gson gson) {
        return new AutoValue_GiphyGifOnlyImage.GsonTypeAdapter(gson);
    }
}
