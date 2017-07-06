package dptsolutions.com.giphysearch.features.search;

import android.support.annotation.IntRange;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dptsolutions.com.giphysearch.MvpBasePresenter;
import dptsolutions.com.giphysearch.dagger.Giphy;
import dptsolutions.com.giphysearch.repositories.GifRepository;
import dptsolutions.com.giphysearch.repositories.models.Gif;
import dptsolutions.com.giphysearch.repositories.models.Rating;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Presenter for {@link SearchView}
 */

class SearchPresenter extends MvpBasePresenter<SearchView> {
    private final GifRepository gifRepository;
    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private ReplaySubject<Integer> paginator;

    @Inject
    SearchPresenter(@Giphy GifRepository gifRepository) {
        this.gifRepository = gifRepository;
    }

    public void setSearch(final String searchTerms, final Rating rating) {
        if(subscriptions.hasSubscriptions()) {
            subscriptions.clear();
        }

        paginator = ReplaySubject.createWithTime(1, TimeUnit.MINUTES, AndroidSchedulers.mainThread());

        subscriptions.add(
                paginator.onBackpressureBuffer()
                    .concatMap(new Func1<Integer, Observable<List<Gif>>>() {
                        @Override
                        public Observable<List<Gif>> call(final Integer page) {
                            Timber.d("paginator concatMap page[%d]", page);
                            return gifRepository.search(searchTerms, page, rating);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<Gif>>() {
                        @Override
                        public void call(List<Gif> gifs) {
                            Timber.d("Got page of gifs");
                            if(isViewAttached()) {
                                if(gifs.size() > 0) {
                                    getView().addGifs(gifs);
                                } else {
                                    getView().showNoResults();
                                }
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Timber.e(throwable, "Error getting page of gifs");
                            if(isViewAttached()) {
                                getView().showError();
                            }
                        }
                    })
        );
    }

    public void getPage(@IntRange(from = 0) int page) {
        if(page < 0) {
            throw new IllegalArgumentException("page can't be negative");
        }

        if(isViewAttached()) {
            if(page == 0) {
                getView().clear();
                getView().showLoading();
            }
            paginator.onNext(page);
        }

    }

    public List<Rating> getSupportedRatings() {
        return gifRepository.supportedRatings();
    }

    @Override
    public void detachView() {
        subscriptions.unsubscribe();
        super.detachView();
    }

    public void gifSelected(Gif gif) {
        if(isViewAttached()) {
            getView().displayOriginalVersion(gif);
        }
    }
}
