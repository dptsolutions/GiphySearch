package dptsolutions.com.giphysearch.repositories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import dptsolutions.com.giphysearch.repositories.models.Gif;
import dptsolutions.com.giphysearch.repositories.models.Rating;
import rx.Observable;
import rx.Single;

/**
 * Repository for retrieving gifs
 */

public interface GifRepository {

    /**
     * Searches for a page of results meeting the search criteria
     *
     * @param searchTerms Terms results should match
     * @param page Page of results to retrieve (0 is first page)
     * @param rating Optional. If specified, results will be filtered to rating
     *
     * @return List of results
     */
    Observable<List<Gif>> search(@NonNull List<String> searchTerms, int page, @Nullable Rating rating);

    /**
     * Retrieve individual gif
     *
     * @param id ID of gif
     *
     * @return The gif
     */
    Single<Gif> get(@NonNull String id);

    /**
     * The supported ratings the GifRepository can handle
     * @return
     */
    List<Rating> supportedRatings();
}
