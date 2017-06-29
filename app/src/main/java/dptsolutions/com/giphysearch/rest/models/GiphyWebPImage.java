package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * A GIF that has versions with .gif and .webp formats
 */
@AutoValue
public abstract class GiphyWebPImage implements GiphyImageBase, GiphyImageWebP {

    /**
     * The size of this GIF in bytes.
     */
    public abstract String size();

    public static TypeAdapter<GiphyWebPImage> typeAdapter(Gson gson) {
        return new AutoValue_GiphyWebPImage.GsonTypeAdapter(gson);
    }
}
