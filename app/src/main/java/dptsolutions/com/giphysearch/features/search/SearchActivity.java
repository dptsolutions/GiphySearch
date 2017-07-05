package dptsolutions.com.giphysearch.features.search;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.search_box)
    EditText searchBox;
    @BindView(R.id.search_button)
    ImageButton searchButton;

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

    Drawable searchIcon;

    @BindColor(R.color.colorAccent)
    int accentColor;

    @Inject
    SearchPresenter searchPresenter;

    @Inject
    GifAdapter gifAdapter;

    @Inject
    FlexboxLayoutManager layoutManager;

    private String currentSearchTerms = "";
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
        initRatingsFab();
        initToolbar();
        searchPresenter.attachView(this);
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

        if(currentPage == -1) {
            startNewSearch();
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
        gifRecyclerView.scrollToPosition(0);
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

    private void initToolbar() {
        searchIcon = DrawableCompat.wrap(AppCompatResources.getDrawable(this,R.drawable.ic_search_black_24dp));
        searchIcon.setTint(accentColor);
        searchButton.setImageDrawable(searchIcon);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                //Do the search if they press the search softkey on their keyboard, or the enter key if they
                //have a hard keyboard
                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    onSearchClick();
                    handled = true;
                }

                return handled;
            }
        });
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
            ratingFab.setColorNormal(accentColor);
            ratingsFab.addButton(ratingFab);
        }
    }

    private void startNewSearch() {
        searchPresenter.setSearch(currentSearchTerms, currentRating);
        currentPage = 0;
        searchPresenter.getPage(currentPage);
    }

    private View.OnClickListener ratingFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Rating selectedRating = (Rating) v.getTag(R.id.rating);
            if(selectedRating != currentRating) {
                currentRating = selectedRating;
                startNewSearch();
            }

            ratingsFab.toggle();
        }
    };

    @OnClick(R.id.search_button)
    void onSearchClick() {
        //Hide the soft keyboard
        InputMethodManager imm = (InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(SearchActivity.this.findViewById(android.R.id.content).getWindowToken(), 0);
        
        String searchText = searchBox.getText().toString();
        if(TextUtils.isEmpty(searchText)) {
            currentSearchTerms = "";
        } else {
            currentSearchTerms = searchText;
        }
        startNewSearch();
    }
}
