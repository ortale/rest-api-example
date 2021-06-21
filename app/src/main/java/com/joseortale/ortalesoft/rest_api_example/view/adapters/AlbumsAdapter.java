package com.joseortale.ortalesoft.rest_api_example.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.joseortale.ortalesoft.rest_api_example.R;
import com.joseortale.ortalesoft.rest_api_example.databinding.ItemAlbumBinding;
import com.joseortale.ortalesoft.rest_api_example.model.Album;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {
    private List<Album> data;
    private LayoutInflater inflater;

    public AlbumsAdapter(Context context, List<Album> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAlbumBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_album, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album productResponse = data.get(position);
        holder.bind(productResponse);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemAlbumBinding binding;

        public ViewHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Album album) {
            binding.tvTitle.setText(String.valueOf(album.getTitle()));
        }

        @Override
        public void onClick(View view) {

        }
    }
}
