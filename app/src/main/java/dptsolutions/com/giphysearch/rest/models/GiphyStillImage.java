package dptsolutions.com.giphysearch.rest.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Data surrounding a static image of a GIF
 */
@AutoValue
public abstract class GiphyStillImage implements GiphyImageBase {

    public static TypeAdapter<GiphyStillImage> typeAdapter(Gson gson) {
        return new AutoValue_GiphyStillImage.GsonTypeAdapter(gson);
    }
}
