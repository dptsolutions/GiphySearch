package dptsolutions.com.giphysearch.features.search;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dptsolutions.com.giphysearch.GlideApp;
import dptsolutions.com.giphysearch.R;
import dptsolutions.com.giphysearch.dagger.ScreenColumnCount;
import dptsolutions.com.giphysearch.recyclerview.EndlessRecyclerOnScrollListener;
import dptsolutions.com.giphysearch.repositories.models.Gif;
import dptsolutions.com.giphysearch.repositories.models.Rating;
import iammert.com.view.scalinglib.ScalingLayout;
import iammert.com.view.scalinglib.ScalingLayoutListener;
import iammert.com.view.scalinglib.State;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class SearchActivity extends AppCompatActivity implements SearchView {

    private static final String STATE_KEY_SEARCH_TERMS = "state.search.terms";
    private static final String STATE_KEY_CURRENT_PAGE = "state.current.page";
    private static final String STATE_KEY_CURRENT_RATING = "state.current.rating";
    private static final String STATE_LOADED_ITEMS = "state.loaded.items";

    @BindView(R.id.recycler_view)
    RecyclerView gifRecyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgressBar;
    @BindView(R.id.click_catcher)
    View clickCatcher;
    @BindView(R.id.fab_icon)
    TextView fabIcon;
    @BindView(R.id.rating_toolbar)
    LinearLayout ratingsToolbar;
    @BindView(R.id.scaling_layout)
    ScalingLayout scalingLayout;
    @BindView(R.id.search_box)
    EditText searchBox;
    @BindView(R.id.search_button)
    ImageButton searchButton;
    @BindView(R.id.no_results)
    TextView noResults;
    @BindView(R.id.overlay)
    View overlay;
    @BindView(R.id.original_gif)
    ImageView originalGif;

    @BindString(R.string.rating_everyone_label)
    String ratingEveryoneLabel;
    @BindString(R.string.rating_teen_label)
    String ratingTeenLabel;
    @BindString(R.string.rating_adult_label)
    String ratingAdultLabel;
    @BindString(R.string.rating_nsfw_label)
    String ratingNsfwLabel;
    @BindString(R.string.rating_na_label)
    String ratingNotAvailableLabel;
    @BindString(R.string.rating_everyone_short_label)
    String ratingEveryoneShortLabel;
    @BindString(R.string.rating_teen_short_label)
    String ratingTeenShortLabel;
    @BindString(R.string.rating_adult_short_label)
    String ratingAdultShortLabel;
    @BindString(R.string.rating_nsfw_short_label)
    String ratingNsfwShortLabel;
    @BindString(R.string.rating_na_short_label)
    String ratingNotAvailableShortLabel;

    Drawable searchIcon;

    @BindColor(R.color.colorAccent)
    int accentColor;

    @Inject
    SearchPresenter searchPresenter;

    @Inject
    GifAdapter gifAdapter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    @ScreenColumnCount
    int columnCount;

    private String currentSearchTerms = "";
    private Rating currentRating = Rating.EVERYONE;
    private int currentPage = -1;
    private EndlessRecyclerOnScrollListener nextPageScrollListener;
    private Snackbar errorBar;
    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        subscriptions = new CompositeSubscription();

        initRecyclerView();
        initRatingsFab();
        initToolbar();
        initErrorSnackbar();

        if(savedInstanceState != null) {
            currentSearchTerms = savedInstanceState.getString(STATE_KEY_SEARCH_TERMS, "");
            currentPage = savedInstanceState.getInt(STATE_KEY_CURRENT_PAGE, -1);
            currentRating = Enum.valueOf(Rating.class, savedInstanceState.getString(STATE_KEY_CURRENT_RATING, Rating.EVERYONE.name()));
            searchPresenter.setSearch(currentSearchTerms, currentRating);
            ArrayList<Gif> loadedGifs = savedInstanceState.getParcelableArrayList(STATE_LOADED_ITEMS);
            if (loadedGifs != null && loadedGifs.size() > 0) {
                addGifs(loadedGifs);
            }
        }

        setFabIcon(currentRating);

        subscriptions.add(gifAdapter.getSelectedGifObservable().subscribe(gif -> searchPresenter.gifSelected(gif)));
        searchPresenter.attachView(this);
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
        outState.putString(STATE_KEY_SEARCH_TERMS, currentSearchTerms);
        outState.putInt(STATE_KEY_CURRENT_PAGE, currentPage);
        outState.putString(STATE_KEY_CURRENT_RATING, currentRating.name());
        outState.putParcelableArrayList(STATE_LOADED_ITEMS, gifAdapter.getGifs());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        searchPresenter.detachView();
        subscriptions.clear();
        super.onDestroy();
    }

    @Override
    public void addGifs(List<Gif> newGifs) {
        gifAdapter.addGifs(newGifs);
        loadingProgressBar.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
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
        loadingProgressBar.setVisibility(View.GONE);
        errorBar.show();
    }

    @Override
    public void showLoading() {
        Timber.d("In showLoading");
        gifRecyclerView.setVisibility(View.GONE);
        noResults.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoResults() {
        loadingProgressBar.setVisibility(View.GONE);
        if(currentPage == 0) {

            //Only show this if we got no results on the first page
            gifRecyclerView.setVisibility(View.GONE);
            noResults.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayOriginalVersion(Gif gif) {
        GlideApp.with(this)
                .asGif()
                .load(gif.fullUrl())
                .fitCenter()
                .placeholder(R.drawable.giphy_logo)
                .into(originalGif);
        overlay.setVisibility(View.VISIBLE);
    }

    private void initToolbar() {
        searchIcon = DrawableCompat.wrap(AppCompatResources.getDrawable(this,R.drawable.ic_search_white_24dp));
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
        LayoutInflater inflater = LayoutInflater.from(this);
        for(Rating rating : searchPresenter.getSupportedRatings()) {
            String label;
            String icon;
            switch (rating) {
                case EVERYONE:
                    label = ratingEveryoneLabel;
                    icon = ratingEveryoneShortLabel;
                    break;
                case TEEN:
                    label = ratingTeenLabel;
                    icon = ratingTeenShortLabel;
                    break;
                case ADULT:
                    label = ratingAdultLabel;
                    icon = ratingAdultShortLabel;
                    break;
                case NSFW:
                    label = ratingNsfwLabel;
                    icon = ratingNsfwShortLabel;
                    break;
                case NA:
                    label = ratingNotAvailableLabel;
                    icon = ratingNotAvailableShortLabel;
                    break;
                default:
                    //Ignore unknown
                    Timber.w("Unknown rating %s. Skipping adding rating to ratingsToolbar.", rating);
                    continue;
            }

            View ratingButton = inflater.inflate(R.layout.rating_filter_item, ratingsToolbar, false);
            TextView iconView = ratingButton.findViewById(R.id.icon);
            TextView labelView = ratingButton.findViewById(R.id.text);
            iconView.setText(icon);
            labelView.setText(label);
            ratingButton.setTag(R.id.rating, rating);
            ratingButton.setOnClickListener(ratingFabClickListener);
            ratingsToolbar.addView(ratingButton);
        }

        scalingLayout.setListener(new ScalingLayoutListener() {
            @Override
            public void onCollapsed() {
                ViewCompat.animate(fabIcon).alpha(1).setDuration(150).start();
                ViewCompat.animate(ratingsToolbar).alpha(0).setDuration(150).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        fabIcon.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        ratingsToolbar.setVisibility(View.INVISIBLE);
                        clickCatcher.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
            }

            @Override
            public void onExpanded() {
                ViewCompat.animate(fabIcon).alpha(0).setDuration(200).start();
                ViewCompat.animate(ratingsToolbar).alpha(1).setDuration(200).setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        ratingsToolbar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        fabIcon.setVisibility(View.INVISIBLE);
                        clickCatcher.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
            }

            @Override
            public void onProgress(float progress) {
                if (progress > 0) {
                    fabIcon.setVisibility(View.INVISIBLE);
                }

                if(progress < 1){
                    ratingsToolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        scalingLayout.setOnClickListener(view -> {
            if (scalingLayout.getState() == State.COLLAPSED) {
                scalingLayout.expand();
            }
        });

        findViewById(R.id.click_catcher).setOnClickListener(view -> {
            if (scalingLayout.getState() == State.EXPANDED) {
                scalingLayout.collapse();
            }
        });


    }

    private void initRecyclerView() {
        nextPageScrollListener = new EndlessRecyclerOnScrollListener(layoutManager, columnCount * 5) {
            @Override
            public void onLoadMore(int nextPage) {
                loadNextPage();
            }
        };
        gifRecyclerView.setLayoutManager(layoutManager);
        gifRecyclerView.setAdapter(gifAdapter);
        gifRecyclerView.addOnScrollListener(nextPageScrollListener);
    }

    private void initErrorSnackbar() {
        errorBar = Snackbar.make(gifRecyclerView, R.string.loading_error, Snackbar.LENGTH_INDEFINITE);
        errorBar.setAction(R.string.action_retry, v -> {
            //Since an error was thrown, we need to reset the search
            searchPresenter.setSearch(currentSearchTerms, currentRating);
            loadCurrentPage();
            errorBar.dismiss();
        });
    }

    private void startNewSearch() {
        searchPresenter.setSearch(currentSearchTerms, currentRating);
        currentPage = 0;
        nextPageScrollListener.reset();
        searchPresenter.getPage(currentPage);
    }

    private void loadNextPage() {
        currentPage++;
        searchPresenter.getPage(currentPage);
    }

    private void loadCurrentPage() {
        searchPresenter.getPage(currentPage);
    }

    private void setFabIcon(Rating rating) {
        switch (rating) {
            case EVERYONE:
                fabIcon.setText(ratingEveryoneShortLabel);
                break;
            case TEEN:
                fabIcon.setText(ratingTeenShortLabel);
                break;
            case ADULT:
                fabIcon.setText(ratingAdultShortLabel);
                break;
            case NSFW:
                fabIcon.setText(ratingNsfwShortLabel);
                break;
            case NA:
                fabIcon.setText(ratingNotAvailableShortLabel);
                break;
            default:
                //Ignore unknown
                Timber.w("Unknown rating %s. Skipping setting fab icon.", rating);
        }
    }

    private View.OnClickListener ratingFabClickListener = v -> {
        Rating selectedRating = (Rating) v.getTag(R.id.rating);
        if(selectedRating != currentRating) {
            currentRating = selectedRating;
            setFabIcon(currentRating);
            startNewSearch();
        }

        scalingLayout.collapse();
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

    @OnClick(R.id.overlay)
    void onOverlayClick() {
        overlay.setVisibility(View.GONE);
    }
}
