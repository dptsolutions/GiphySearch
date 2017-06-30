package dptsolutions.com.giphysearch.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Dagger qualifier for Application Context
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface ApplicationContext {
}
