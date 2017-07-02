package dptsolutions.com.giphysearch.features.search;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by donal on 7/1/2017.
 */
@AutoValue
public abstract class SearchRequest {

    public abstract List<String> searchTerms();
    public abstract int page();

    public static SearchRequest create(List<String> searchTerms, int page) {
        return new AutoValue_SearchRequest(searchTerms, page);
    }
}
