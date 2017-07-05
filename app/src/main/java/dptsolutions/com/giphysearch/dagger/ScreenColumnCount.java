package dptsolutions.com.giphysearch.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Dagger qualifier for # of columns for the given screen size/orientation
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface ScreenColumnCount {
}
