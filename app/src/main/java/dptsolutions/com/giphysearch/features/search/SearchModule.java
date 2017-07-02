package dptsolutions.com.giphysearch.features.search;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

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
    static FlexboxLayoutManager provideFlexboxLayoutManager(SearchActivity activity) {
        FlexboxLayoutManager layoutManager =new FlexboxLayoutManager(activity);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        return layoutManager;
    }

}
