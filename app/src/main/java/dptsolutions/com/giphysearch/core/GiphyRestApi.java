package dptsolutions.com.giphysearch.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Dagger qualifier for Giphy REST API related dependencies
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface GiphyRestApi {
}
