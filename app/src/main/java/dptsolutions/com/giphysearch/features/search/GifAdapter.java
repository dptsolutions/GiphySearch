package dptsolutions.com.giphysearch.features.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dptsolutions.com.giphysearch.GlideApp;
import dptsolutions.com.giphysearch.R;
import dptsolutions.com.giphysearch.dagger.ScreenColumnCount;
import dptsolutions.com.giphysearch.repositories.models.Gif;

/**
 * RecyclerView Adapter for gifs
 */
class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private final int columnCount;
    private ArrayList<Gif> gifs = new ArrayList<>();

    @Inject
    GifAdapter(@ScreenColumnCount int columnCount) {

        this.columnCount = columnCount;
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View gifView = inflater.inflate(R.layout.gif_tile, viewGroup, false);
        return new GifViewHolder(gifView);
    }

    @Override
    public void onBindViewHolder(GifViewHolder viewHolder, int position) {
        Gif gif = gifs.get(position);
        viewHolder.bind(gif, position % columnCount == 0);
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
        void bind(Gif gif, boolean doBreak) {
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