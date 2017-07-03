package dptsolutions.com.giphysearch.repositories.models;

/**
 * Repository level ratings for {@link Gif}
 */
public enum Rating {
    /**
     * Suitable for everyone: kids, teens, adults
     */
    EVERYONE,
    /**
     * Suitable for teens and up
     */
    TEEN,
    /**
     * Suitable for adults
     */
    ADULT,
    /**
     * Include the dirty stuff
     */
    NSFW,
    /**
     * No rating available
     */
    NA;
}
