package dptsolutions.com.giphysearch.features.search;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Dagger 2 Module for {@link SearchActivity}
 */
@Module
public abstract class SearchModule {

    @Binds
    abstract SearchView provideSearchView(SearchActivity searchActivity);

    @Provides
    static CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
