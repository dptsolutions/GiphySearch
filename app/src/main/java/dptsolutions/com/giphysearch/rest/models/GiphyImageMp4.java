package dptsolutions.com.giphysearch.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * MP4 properties of a Giphy Image
 */

interface GiphyImageMp4 {

    /**
     * The URL for this GIF in .MP4 format.
     */
    @SerializedName("mp4")
    String mp4();

    /**
     * The size in bytes of the .MP4 file corresponding to this GIF.
     */
    @SerializedName("mp4_size")
    String mp4Size();
}
