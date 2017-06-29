package dptsolutions.com.giphysearch.rest.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Wrapper of a paged result from the Giphy API
 */
@AutoValue
public abstract class GiphyPagedResponse {

    public abstract GiphyGif data();

    public abstract GiphyMeta meta();

    public abstract GiphyPagination pagination();

    public static TypeAdapter<GiphyPagedResponse> typeAdapter(Gson gson) {
        return new AutoValue_GiphyPagedResponse.GsonTypeAdapter(gson);
    }

}
