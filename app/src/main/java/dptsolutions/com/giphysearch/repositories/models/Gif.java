package dptsolutions.com.giphysearch.repositories.models;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.parcel.ParcelAdapter;

import dptsolutions.com.giphysearch.repositories.models.parceladapters.RatingParcelAdapter;

/**
 * Presentation model for a Gif
 */
@AutoValue
public abstract class Gif implements Parcelable {

    /**
     * ID of gif
     */
    public abstract String id();

    /**
     * URL to full sized gif
     */
    public abstract String fullUrl();

    /**
     * URL to preview sized gif
     */
    public abstract String previewUrl();

    /**
     * Rating of gif
     */
    @ParcelAdapter(RatingParcelAdapter.class)
    public abstract Rating rating();

    public static Builder builder() {
        return new AutoValue_Gif.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(@NonNull String id);

        public abstract Builder fullUrl(@NonNull String fullUrl);

        public abstract Builder previewUrl(@NonNull String previewUrl);

        public abstract Builder rating(@NonNull Rating rating);

        public abstract Gif build();
    }


}
