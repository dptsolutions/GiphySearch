package dptsolutions.com.giphysearch;

import android.app.Activity;
import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dptsolutions.com.giphysearch.core.ApplicationModule;
import dptsolutions.com.giphysearch.core.DaggerApplicationComponent;
import timber.log.Timber;

public class GiphySearchApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
