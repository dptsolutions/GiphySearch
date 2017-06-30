package dptsolutions.com.giphysearch.repositories.models.parceladapters;

import android.os.Parcel;

import com.ryanharter.auto.value.parcel.TypeAdapter;

import dptsolutions.com.giphysearch.repositories.models.Rating;

/**
 * {@link TypeAdapter} for {@link Rating}
 */
public class RatingParcelAdapter implements TypeAdapter<Rating> {
    @Override
    public Rating fromParcel(Parcel in) {
        return Rating.valueOf(in.readString());
    }

    @Override
    public void toParcel(Rating rating, Parcel out) {
        out.writeString(rating.name());
    }
}
