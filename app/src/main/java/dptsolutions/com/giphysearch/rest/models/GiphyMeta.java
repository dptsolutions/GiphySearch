package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Contains basic information regarding the request, whether it was successful, and the response given by the API.
 *
 * @see <a href="https://developers.giphy.com/docs/#metacontent-object">https://developers.giphy.com/docs/#metacontent-object</a>
 */
@AutoValue
public abstract class GiphyMeta {

    /**
     * HTTP Response Message
     */
    public abstract String msg();

    /**
     * HTTP Response Code
     */
    public abstract int status();

    /**
     * A unique ID paired with this response from the API.
     */
    @SerializedName("response_id")
    public abstract String responseId();

    public static TypeAdapter<GiphyMeta> typeAdapter(Gson gson) {
        return new AutoValue_GiphyMeta.GsonTypeAdapter(gson);
    }
}
