package dptsolutions.com.giphysearch.repositories.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dagger.Reusable;
import dptsolutions.com.giphysearch.repositories.GifRepository;
import dptsolutions.com.giphysearch.repositories.models.Gif;
import dptsolutions.com.giphysearch.repositories.models.Rating;
import dptsolutions.com.giphysearch.rest.GiphyApi;
import dptsolutions.com.giphysearch.rest.models.GiphyGif;
import dptsolutions.com.giphysearch.rest.models.GiphyPagedResponse;
import dptsolutions.com.giphysearch.rest.models.GiphyResponse;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

/**
 * Implementation powered by Giphy
 */
@Reusable
public class GiphyGifRepository implements GifRepository {

    private static final List<Rating> SUPPORTED_RATINGS =
            Arrays.asList(Rating.EVERYONE, Rating.TEEN, Rating.ADULT, Rating.NSFW);
    private static final int PAGE_LIMIT = 50;
    private final GiphyApi giphyApi;

    public GiphyGifRepository(GiphyApi giphyApi) {

        this.giphyApi = giphyApi;
    }

    @Override
    public Observable<List<Gif>> search(@NonNull String searchTerms, int page, @Nullable Rating rating) {
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

        Observable<GiphyPagedResponse> searchObservable = TextUtils.isEmpty(searchTerms)
                ? giphyApi.getTrending(giphyRating)
                : giphyApi.searchGifs(searchTerms, PAGE_LIMIT, page * PAGE_LIMIT, giphyRating);

        return searchObservable
                .flatMap(new Func1<GiphyPagedResponse, Observable<List<Gif>>>() {
                    @Override
                    public Observable<List<Gif>> call(GiphyPagedResponse giphyPagedResponse) {
                        List<Gif> results = giphyPagedResponse.data().size() > 0
                                ? new ArrayList<Gif>(giphyPagedResponse.data().size())
                                : Collections.<Gif>emptyList();
                        for(GiphyGif gif : giphyPagedResponse.data()) {
                            Gif result = Gif.builder()
                                    .id(gif.id())
                                    .fullUrl(gif.images().original().url())
                                    .previewUrl(gif.images().fixedHeightDownsampled().url())
                                    .rating(parseRating(gif.rating()))
                                    .build();
                            results.add(result);
                        }
                        return Observable.just(results);
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

    @Override
    public List<Rating> supportedRatings() {
        return SUPPORTED_RATINGS;
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
