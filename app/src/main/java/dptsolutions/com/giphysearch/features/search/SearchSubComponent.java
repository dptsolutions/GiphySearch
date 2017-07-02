package dptsolutions.com.giphysearch.features.search;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Dagger 2 Subcomponent for {@link SearchActivity}
 */
@Subcomponent(modules = {SearchModule.class})
public interface SearchSubComponent extends AndroidInjector<SearchActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SearchActivity> {
    }
}
