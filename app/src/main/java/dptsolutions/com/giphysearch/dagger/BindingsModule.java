package dptsolutions.com.giphysearch.dagger;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import dptsolutions.com.giphysearch.features.search.SearchActivity;
import dptsolutions.com.giphysearch.features.search.SearchSubComponent;

/**
 * Dagger 2 module for building Application SubComponent bindings
 */
@Module
public abstract class BindingsModule {

    @Binds
    @IntoMap
    @ActivityKey(SearchActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindFeatureActivityInjectorFactory(SearchSubComponent.Builder builder);
}
