package dptsolutions.com.giphysearch;

import android.content.Context;

import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import dptsolutions.com.giphysearch.dagger.ApplicationComponent;

/**
 * {@link AppGlideModule} for the app
 */
@Excludes(OkHttpLibraryGlideModule.class)
@GlideModule
public class GiphySearchAppGlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(Context context, Registry registry) {
        super.registerComponents(context, registry);
        //This registers the OkHttpClient
        //There has to be a better way to do this, but I don't see how at the moment.
        ApplicationComponent applicationComponent =
                ((GiphySearchApplication)context.getApplicationContext()).getApplicationComponent();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(applicationComponent.imageHttpClient()));
    }
}
