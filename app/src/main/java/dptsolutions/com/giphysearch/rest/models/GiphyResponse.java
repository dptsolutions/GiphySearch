package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Wrapper of a result from the Giphy API
 */
@AutoValue
public abstract class GiphyResponse {

    public abstract GiphyGif data();

    public abstract GiphyMeta meta();

    public static TypeAdapter<GiphyResponse> typeAdapter(Gson gson) {
        return new AutoValue_GiphyResponse.GsonTypeAdapter(gson);
    }

}
