package dptsolutions.com.giphysearch.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Dagger qualifier for Giphy related dependencies
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Giphy {
}
