package dptsolutions.com.giphysearch.rest.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * The User Object contains information about the user associated with a GIF and URLs to assets such as that user's avatar image, profile, and more.
 */
@AutoValue
public abstract class GiphyUser {

    /**
     * The URL for this user's avatar image.
     */
    @SerializedName("avatar_url")
    public abstract String avatarUrl();

    /**
     * The URL for the banner image that appears atop this user's profile page.
     */
    @SerializedName("banner_url")
    public abstract String bannerUrl();

    /**
     * The URL for this user's profile.
     */
    @SerializedName("profile_url")
    public abstract String profileUrl();

    /**
     * The username associated with this user.
     */
    public abstract String username();

    /**
     * The display name associated with this user (contains formatting the base username might not).
     */
    @SerializedName("display_name")
    public abstract String displayName();

    /**
     * The Twitter username associated with this user, if applicable.
     */
    @Nullable
    public abstract String twitter();

    public static TypeAdapter<GiphyUser> typeAdapter(Gson gson) {
        return new AutoValue_GiphyUser.GsonTypeAdapter(gson);
    }
}
