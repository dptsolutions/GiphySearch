package dptsolutions.com.giphysearch.rest.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * The Pagination Object contains information relating to the number of total results available as well as the number of results fetched and their relative positions.
 *
 * @see <a href="https://developers.giphy.com/docs/#pagination-object">https://developers.giphy.com/docs/#pagination-object</a>
 */
@AutoValue
public abstract class GiphyPagination {

    /**
     * Position in pagination.
     */
    public abstract int offset();

    /**
     * Total number of items available.
     */
    public abstract int totalCount();

    /**
     * Total number of items returned.
     */
    public abstract int count();

    public static TypeAdapter<GiphyPagination> typeAdapter(Gson gson) {
        return new AutoValue_GiphyPagination.GsonTypeAdapter(gson);
    }
}
