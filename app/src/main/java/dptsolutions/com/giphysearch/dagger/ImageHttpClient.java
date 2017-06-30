package dptsolutions.com.giphysearch.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Dagger qualifier for OkHttp client that always sends Accept image/* header in request
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface ImageHttpClient {
}
