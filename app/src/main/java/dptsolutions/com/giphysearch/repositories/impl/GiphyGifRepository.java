package dptsolutions.com.giphysearch.repositories.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dptsolutions.com.giphysearch.repositories.GifRepository;
import dptsolutions.com.giphysearch.repositories.models.Gif;
import dptsolutions.com.giphysearch.repositories.models.Rating;
import dptsolutions.com.giphysearch.rest.GiphySearchApi;
import dptsolutions.com.giphysearch.rest.models.GiphyGif;
import dptsolutions.com.giphysearch.rest.models.GiphyPagedResponse;
import dptsolutions.com.giphysearch.rest.models.GiphyResponse;
import rx.Single;
import rx.functions.Func1;

/**
 * Created by donal on 6/29/2017.
 */

public class GiphyGifRepository implements GifRepository {

    private static final int PAGE_LIMIT = 25;
    private final GiphySearchApi giphyApi;

    public GiphyGifRepository(GiphySearchApi giphyApi) {

        this.giphyApi = giphyApi;
    }
    @Override
    public Single<List<Gif>> search(@NonNull List<String> searchTerms, int page, @Nullable Rating rating) {
        String searchString = TextUtils.join(",", searchTerms);
        String giphyRating = null;
        if(rating != null) {
            switch (rating) {
                case ADULT:
                    giphyRating = "r";
                    break;
                case TEEN:
                    giphyRating = "pg-13";
                    break;
                case EVERYONE:
                    giphyRating = "g";
                    break;
                case NSFW:
                    giphyRating = "nsfw";
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported rating[" + rating + "]");
            }
        }
        return giphyApi.searchGifs(searchString, PAGE_LIMIT, page * PAGE_LIMIT, giphyRating)
                .flatMap(new Func1<GiphyPagedResponse, Single<List<Gif>>>() {
                    @Override
                    public Single<List<Gif>> call(GiphyPagedResponse giphyPagedResponse) {
                        List<Gif> results = giphyPagedResponse.data().size() > 0
                                ? new ArrayList<Gif>(giphyPagedResponse.data().size())
                                : Collections.<Gif>emptyList();
                        for(GiphyGif gif : giphyPagedResponse.data()) {
                            Gif result = Gif.builder()
                                    .id(gif.id())
                                    .fullUrl(gif.images().original().url())
                                    .previewUrl(gif.images().previewGif().url())
                                    .rating(parseRating(gif.rating()))
                                    .build();
                            results.add(result);
                        }
                        return Single.just(results);
                    }
                });
    }

    @Override
    public Single<Gif> get(@NonNull String id) {
        return giphyApi.getGifById(id)
                .flatMap(new Func1<GiphyResponse, Single<Gif>>() {
                    @Override
                    public Single<Gif> call(GiphyResponse giphyResponse) {
                        GiphyGif gif = giphyResponse.data();
                        Gif result = Gif.builder()
                                .id(gif.id())
                                .fullUrl(gif.images().original().url())
                                .previewUrl(gif.images().previewGif().url())
                                .rating(parseRating(gif.rating()))
                                .build();
                        return Single.just(result);
                    }
                });
    }

    private Rating parseRating(String giphyRating) {
        switch (giphyRating) {
            case "y":
            case "g":
                return Rating.EVERYONE;
            case "pg":
            case "pg-13":
                return Rating.TEEN;
            case "r":
                return Rating.ADULT;
            case "nsfw":
                return Rating.NSFW;
            default:
                return Rating.NA;
        }
    }
}
