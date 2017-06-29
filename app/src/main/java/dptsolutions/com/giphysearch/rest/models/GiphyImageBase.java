package dptsolutions.com.giphysearch.rest.models;

/**
 * Base properties of a Giphy Image
 */

public interface GiphyImageBase {

    /**
     * The publicly-accessible direct URL for this GIF.
     */
    String url();

    /**
     * The width of this GIF in pixels.
     */
    String width();

    /**
     * The height of this GIF in pixels.
     */
    String height();
}
