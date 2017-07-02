package dptsolutions.com.giphysearch.features.search;

import java.util.List;

import dptsolutions.com.giphysearch.MvpView;
import dptsolutions.com.giphysearch.repositories.models.Gif;

/**
 * View for gif search results
 */

interface SearchView extends MvpView {

    void addGifs(List<Gif> newGifs);

    void clear();

    void showError();

    void showLoading();
}
