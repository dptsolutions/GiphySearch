package dptsolutions.com.giphysearch.features.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;

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
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View gifView = inflater.inflate(R.layout.gif_tile, viewGroup, false);
        return new GifViewHolder(gifView);
    }

    @Override
    public void onBindViewHolder(GifViewHolder viewHolder, int position) {
        Gif gif = gifs.get(position);
        viewHolder.bind(gif, position % 2 == 0);
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
        void bind(Gif gif, boolean doBreak) {
            this.gif = gif;
            GlideApp.with(gifPreview)
                    .asGif()
                    .load(gif.previewUrl())
                    .centerCrop()
                    .into(gifPreview);

            ViewGroup.LayoutParams lp = gifPreview.getLayoutParams();
            if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
                flexboxLp.setWrapBefore(doBreak);
                flexboxLp.setFlexGrow(0.5f);
            }
        }

        /**
         * Get the Gif currently bound to this ViewHolder
         */
        Gif getGif() {
            return gif;
        }


    }
}