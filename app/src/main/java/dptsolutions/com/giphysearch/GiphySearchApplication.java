package dptsolutions.com.giphysearch;

import android.app.Activity;
import android.app.Application;

import com.bumptech.glide.Glide;
import com.jakewharton.threetenabp.AndroidThreeTen;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dptsolutions.com.giphysearch.dagger.ApplicationComponent;
import dptsolutions.com.giphysearch.dagger.DaggerApplicationComponent;
import timber.log.Timber;

public class GiphySearchApplication extends Application implements HasActivityInjector {

    private ApplicationComponent applicationComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
        applicationComponent = DaggerApplicationComponent.builder()
                .application(this)
                .build();
        applicationComponent.inject(this);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
