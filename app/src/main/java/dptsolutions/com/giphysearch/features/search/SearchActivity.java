package dptsolutions.com.giphysearch.features.search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dptsolutions.com.giphysearch.R;
import dptsolutions.com.giphysearch.repositories.models.Gif;
import dptsolutions.com.giphysearch.repositories.models.Rating;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

public class SearchActivity extends AppCompatActivity implements SearchView {

    @BindView(R.id.recycler_view)
    RecyclerView gifRecyclerView;

    @BindView(R.id.loading_progress)
    ContentLoadingProgressBar loadingProgressBar;

    @BindView(R.id.fab)
    FloatingActionButton ratingFab;

    @Inject
    SearchPresenter searchPresenter;

    @Inject
    GifAdapter gifAdapter;

    @Inject
    FlexboxLayoutManager layoutManager;

    private List<String> currentSearchTerms = new ArrayList<>();
    private Rating currentRating = Rating.EVERYONE;
    private int currentPage = -1;

    @OnClick(R.id.fab)
    void onRatingFabClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        gifRecyclerView.setLayoutManager(layoutManager);
        gifRecyclerView.setAdapter(gifAdapter);
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
}