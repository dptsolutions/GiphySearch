package dptsolutions.com.giphysearch.features.search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dptsolutions.com.giphysearch.dagger.ScreenColumnCount;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Dagger 2 Module for {@link SearchActivity}
 */
@Module
abstract class SearchModule {

    @Binds
    abstract SearchView provideSearchView(SearchActivity searchActivity);

    @Provides
    static CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

    @Provides
    @ScreenColumnCount
    static int provideScreenColumnCount(SearchActivity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int widthInDp = (int) (dm.widthPixels / dm.density);
        int colCount;

        if(widthInDp <= 420) {
            colCount = 2;
        } else if(420 < widthInDp && widthInDp <= 700) {
            colCount = 3;
        } else if(700 < widthInDp && widthInDp <= 980) {
            colCount = 4;
        } else if(980 < widthInDp) {
            colCount = 5;
        } else {
            Timber.w("What an odd width for a screen - %d", widthInDp);
            colCount = 1;
        }
        Timber.d("widthInDp[%d] screenColumns[%d]", widthInDp, colCount);
        return colCount;
    }

    @Provides
    static GridLayoutManager provideLayoutManager(SearchActivity activity, @ScreenColumnCount int columnCount) {
        GridLayoutManager layoutManager =new GridLayoutManager(activity, columnCount);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

}
