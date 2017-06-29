package dptsolutions.com.giphysearch.rest;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by donal on 6/28/2017.
 */
@GsonTypeAdapterFactory
public abstract class AutoValueTypeAdapterFactory implements TypeAdapterFactory {

    // Static factory method to access the package
    // private generated implementation
    public static TypeAdapterFactory create() {
        return new AutoValueGson_AutoValueTypeAdapterFactory();
    }
}
