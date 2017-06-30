package dptsolutions.com.giphysearch.rest.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * A GIF that has only a one unspecified version (though it's usually a .gif)
 */
@AutoValue
public abstract class GiphyUnspecifiedImage implements GiphyImageBase {

    /**
     * The size of this GIF in bytes.
     */
    public abstract String size();

    public static TypeAdapter<GiphyUnspecifiedImage> typeAdapter(Gson gson) {
        return new AutoValue_GiphyUnspecifiedImage.GsonTypeAdapter(gson);
    }
}
