package dptsolutions.com.giphysearch.features.search;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dptsolutions.com.giphysearch.R;
import dptsolutions.com.giphysearch.repositories.models.Gif;
import dptsolutions.com.giphysearch.repositories.models.Rating;

public class SearchActivity extends AppCompatActivity implements SearchView {

    @BindView(R.id.recycler_view)
    RecyclerView gifRecyclerView;

    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar loadingProgressBar;

    @BindView(R.id.fab)
    FloatingActionsMenu ratingsFab;

    @BindString(R.string.rating_everyone)
    String ratingEveryoneLabel;
    @BindString(R.string.rating_teen)
    String ratingTeenLabel;
    @BindString(R.string.rating_adult)
    String ratingAdultLabel;
    @BindString(R.string.rating_nsfw)
    String ratingNsfwLabel;
    @BindString(R.string.rating_na)
    String ratingNotAvailableLabel;

    @Inject
    SearchPresenter searchPresenter;

    @Inject
    GifAdapter gifAdapter;

    @Inject
    FlexboxLayoutManager layoutManager;

    private List<String> currentSearchTerms = new ArrayList<>();
    private Rating currentRating = Rating.EVERYONE;
    private int currentPage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        gifRecyclerView.setLayoutManager(layoutManager);
        gifRecyclerView.setAdapter(gifAdapter);
        searchPresenter.attachView(this);

        initRatingsFab();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState == null) {
            return;
        }

        //Restore state!
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchPresenter.setSearch(currentSearchTerms, currentRating);
        if(currentPage == -1) {
            currentPage = 0;
            searchPresenter.getPage(currentPage);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Save state!
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        searchPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void addGifs(List<Gif> newGifs) {
        gifAdapter.addGifs(newGifs);
        loadingProgressBar.hide();
        gifRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void clear() {
        //Clear gifs out of recyclerview
        gifAdapter.clear();
    }

    @Override
    public void showError() {
        //Snackbar?
    }

    @Override
    public void showLoading() {
        gifRecyclerView.setVisibility(View.GONE);
        loadingProgressBar.show();
    }

    private void initRatingsFab() {
        for(Rating rating : searchPresenter.getSupportedRatings()) {
            FloatingActionButton ratingFab = new FloatingActionButton(this);
            String label = "";
            switch (rating) {
                case EVERYONE:
                    label = ratingEveryoneLabel;
                    break;
                case TEEN:
                    label = ratingTeenLabel;
                    break;
                case ADULT:
                    label = ratingAdultLabel;
                    break;
                case NSFW:
                    label = ratingNsfwLabel;
                    break;
                case NA:
                    label = ratingNotAvailableLabel;
                    break;
            }
            ratingFab.setTitle(label);
            ratingFab.setTag(R.id.rating, rating);
            ratingFab.setSize(FloatingActionButton.SIZE_MINI);
            ratingFab.setOnClickListener(ratingFabClickListener);
            ratingsFab.addButton(ratingFab);
        }
    }

    private View.OnClickListener ratingFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Rating selectedRating = (Rating) v.getTag(R.id.rating);
            if(selectedRating != currentRating) {
                currentRating = selectedRating;
                searchPresenter.setSearch(Collections.<String>emptyList(), currentRating);
                currentPage = 0;
                searchPresenter.getPage(currentPage);
            }

            ratingsFab.toggle();
        }
    };
}
