package dptsolutions.com.giphysearch.core;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import dptsolutions.com.giphysearch.GiphySearchApplication;

/**
 * Dagger 2 Component suppling application-wide dependencies
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
                      ApplicationModule.class,
                      BindingsModule.class})
public interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(GiphySearchApplication application);
        ApplicationComponent build();
    }

    void inject(GiphySearchApplication application);
}
