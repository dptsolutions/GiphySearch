package dptsolutions.com.giphysearch.core;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.AutoValueGsonExtension;

import org.aaronhe.threetengson.InstantConverter;
import org.aaronhe.threetengson.LocalDateConverter;
import org.aaronhe.threetengson.LocalDateTimeConverter;
import org.aaronhe.threetengson.LocalTimeConverter;
import org.aaronhe.threetengson.OffsetDateTimeConverter;
import org.aaronhe.threetengson.OffsetTimeConverter;
import org.aaronhe.threetengson.ZonedDateTimeConverter;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.OffsetTime;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dptsolutions.com.giphysearch.BuildConfig;
import dptsolutions.com.giphysearch.GiphySearchApplication;
import dptsolutions.com.giphysearch.rest.AutoValueTypeAdapterFactory;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Dagger 2 module for building app-wide dependencies
 */
@Module
public class ApplicationModule {

    //private final Application application;

    //public ApplicationModule(Application application) {
    //    this.application = application;
    //}

    @Provides
    @Singleton
    @ApplicationContext
    Context provideApplicationContext(GiphySearchApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(AutoValueTypeAdapterFactory.create())
                .registerTypeAdapter(Instant.class, new InstantConverter())
                .registerTypeAdapter(LocalDate.class, new LocalDateConverter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeConverter())
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeConverter())
                .registerTypeAdapter(OffsetTime.class, new OffsetTimeConverter())
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(OkHttpClient httpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.GIPHY_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new GiphyApiKeyInjector())
                .build();
    }

    @Provides
    @Singleton
    static Cache provideOkHttpCache(@ApplicationContext Context context) {
        int cacheSize = 50 * 1024 * 1024; // 50 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override public void log(String message) {
                Timber.tag("OkHttp").d(message);
            }
        });
        //Always logging body-level b/c Timber should never be logging debug level messages in production!
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    /**
     * Injects the Giphy API key into requests targeting the Giphy API
     */
    private static final class GiphyApiKeyInjector implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            if(!chain.request().url().host().equalsIgnoreCase(BuildConfig.GIPHY_BASE_URL)) {
                return chain.proceed(chain.request());
            }

            //We're going to the Giphy API, inject api key into query string
            HttpUrl newUrl = chain.request().url().newBuilder().addQueryParameter("api_key",BuildConfig.GIPHY_BASE_URL).build();
            Request newRequest = chain.request().newBuilder().url(newUrl).build();
            return chain.proceed(newRequest);
        }
    }

}