package dptsolutions.com.giphysearch.features.search;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dptsolutions.com.giphysearch.dagger.FlexboxColumnCount;
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
    @FlexboxColumnCount
    static int provideFlexboxColumnCount(SearchActivity activity) {
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
        Timber.d("widthInDp[%d] flexboxColumns[%d]", widthInDp, colCount);
        return colCount;
    }

    @Provides
    static FlexboxLayoutManager provideFlexboxLayoutManager(SearchActivity activity) {
        FlexboxLayoutManager layoutManager =new FlexboxLayoutManager(activity);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        return layoutManager;
    }

}
