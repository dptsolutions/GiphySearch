package dptsolutions.com.giphysearch.features.search;

import android.support.v7.widget.RecyclerView;
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
import dptsolutions.com.giphysearch.repositories.models.Gif;

/**
 * RecyclerView Adapter for gifs
 */
class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private List<Gif> gifs = new ArrayList<>();

    @Inject
    GifAdapter() {}

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        ImageView imageView = new ImageView(viewGroup.getContext());
        imageView.setId(R.id.preview);
        return new GifViewHolder(imageView);
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