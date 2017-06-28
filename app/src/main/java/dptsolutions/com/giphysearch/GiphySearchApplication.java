package dptsolutions.com.giphysearch;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by donal on 6/27/2017.
 */

public class GiphySearchApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        mAppComponent = DaggerApplicationComponent.builder()
//                .applicationModule(new ApplicationModule(this))
//                .build();

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
