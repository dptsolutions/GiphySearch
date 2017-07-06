package dptsolutions.com.giphysearch.features.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dptsolutions.com.giphysearch.GlideApp;
import dptsolutions.com.giphysearch.R;
import dptsolutions.com.giphysearch.dagger.ScreenColumnCount;
import dptsolutions.com.giphysearch.repositories.models.Gif;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * RecyclerView Adapter for gifs
 */
class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private ArrayList<Gif> gifs = new ArrayList<>();
    private PublishSubject<Gif> selectedGifSubject = PublishSubject.create();

    @Inject
    GifAdapter() {
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View gifView = inflater.inflate(R.layout.gif_tile, viewGroup, false);
        final GifViewHolder gifViewHolder = new GifViewHolder(gifView);
        RxView.clicks(gifView)
                .takeUntil(RxView.detaches(viewGroup))
                .map(new Func1<Void, Gif>() {
                    @Override
                    public Gif call(Void aVoid) {
                        return gifViewHolder.getGif();
                    }
                })
                .subscribe(selectedGifSubject);
        return gifViewHolder;
    }

    @Override
    public void onBindViewHolder(GifViewHolder viewHolder, int position) {
        Gif gif = gifs.get(position);
        viewHolder.bind(gif);
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    void clear() {
        gifs.clear();
        notifyDataSetChanged();
    }

    void addGifs(List<Gif> newGifs) {
        gifs.addAll(newGifs);
        notifyDataSetChanged();
    }

    Observable<Gif> getSelectedGifObservable() {
        return selectedGifSubject.asObservable();
    }

    ArrayList<Gif> getGifs() {
        return gifs;
    }

    /**
     * ViewHolder for Gifs
     */
    static class GifViewHolder extends RecyclerView.ViewHolder {

        private Gif gif;

        @BindView(R.id.preview)
        ImageView gifPreview;


        GifViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Binds a Gif to this ViewHolder
         */
        void bind(Gif gif) {
            this.gif = gif;
            GlideApp.with(gifPreview)
                    .asGif()
                    .load(gif.previewUrl())
                    .centerCrop()
                    .placeholder(R.drawable.giphy_logo)
                    .into(gifPreview);

        }

        /**
         * Get the Gif currently bound to this ViewHolder
         */
        Gif getGif() {
            return gif;
        }


    }
}