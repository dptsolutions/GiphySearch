package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * WebP properties of a Giphy Image
 */

public interface GiphyImageWebP {

    /**
     * The URL for this GIF in .webp format.
     */
    @SerializedName("webp")
    String webp();

    /**
     * The size in bytes of the .webp file corresponding to this GIF.
     */
    @SerializedName("webp_size")
    String webpSize();
}
