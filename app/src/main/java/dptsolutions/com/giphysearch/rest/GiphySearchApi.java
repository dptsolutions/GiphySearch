package dptsolutions.com.giphysearch.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dptsolutions.com.giphysearch.rest.models.GiphyPagedResponse;
import dptsolutions.com.giphysearch.rest.models.GiphyResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

/**
 * Retrofit client for Giphy's Search API
 */
public interface GiphySearchApi {

    /**
     * Search the Giphy API
     *
     * @param searchQuery Search query term or prhase.
     * @param limit The maximum number of records to return.
     * @param offset An optional results offset. Defaults to 0.
     * @param rating Filters results by specified rating. Will include every rating lower than the one specified
     *
     * @return The search results
     */
    @GET("v1/gifs/search")
    Single<GiphyPagedResponse> searchGifs(@Query("q") @NonNull String searchQuery,
                                          @Query("limit") @Nullable Integer limit,
                                          @Query("offset") @Nullable Integer offset,
                                          @Query("rating") @Nullable String rating);

    /**
     * Get a specific Gif by ID
     *
     * @param gifId ID of Gif
     *
     * @return The gif
     */
    @GET("v1/gifs/{gif_id}")
    Single<GiphyResponse> getGifById(@Path("gif_id") @NonNull String gifId);


}
