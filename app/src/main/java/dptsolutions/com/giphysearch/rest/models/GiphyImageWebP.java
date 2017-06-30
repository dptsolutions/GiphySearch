package dptsolutions.com.giphysearch.rest.models;

/**
 * WebP properties of a Giphy Image
 */

public interface GiphyImageWebP {

    /**
     * The URL for this GIF in .webp format.
     */
    String webp();

    /**
     * The size in bytes of the .webp file corresponding to this GIF.
     */
    String webpSize();
}
