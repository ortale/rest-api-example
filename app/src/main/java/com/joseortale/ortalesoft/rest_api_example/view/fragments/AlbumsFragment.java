package com.joseortale.ortalesoft.rest_api_example.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joseortale.ortalesoft.rest_api_example.R;
import com.joseortale.ortalesoft.rest_api_example.databinding.FragmentAlbumListBinding;
import com.joseortale.ortalesoft.rest_api_example.helpers.RecyclerItemClickListener;
import com.joseortale.ortalesoft.rest_api_example.helpers.Resource;
import com.joseortale.ortalesoft.rest_api_example.model.Album;
import com.joseortale.ortalesoft.rest_api_example.view.activities.MainActivity;
import com.joseortale.ortalesoft.rest_api_example.view.adapters.AlbumsAdapter;
import com.joseortale.ortalesoft.rest_api_example.viewmodel.AlbumsViewModel;

import java.util.List;

public class AlbumsFragment extends Fragment {
    private final String TAG = AlbumsFragment.class.getSimpleName();

    private Context context;

    private RecyclerView.Adapter accountsAdapter;

    private FragmentAlbumListBinding binding;
    private AlbumsViewModel albumsViewModel;

    public static AlbumsFragment newInstance() {
        AlbumsFragment fragment = new AlbumsFragment();

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_list, container, false);

        onDataLoading();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumsViewModel = ViewModelProviders.of(requireActivity()).get(AlbumsViewModel.class);
        albumsViewModel.getAlbums(context);
        albumsViewModel.getAlbumsRepository().observe(getViewLifecycleOwner(), albumsResponse -> {
            if (albumsResponse.status == Resource.Status.SUCCESS) {
                assert albumsResponse.dataList != null;
                onDataFinishedLoading();
                updateUI(albumsResponse.dataList);
            } else {
                onDataFinishedLoading();
                Toast.makeText(context, albumsResponse.message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onDataLoading() {
        binding.rvAlbums.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    private void onDataFinishedLoading() {
        binding.rvAlbums.setVisibility(View.VISIBLE);
        binding.progressCircular.setVisibility(View.GONE);
    }

    private void updateUI(List<Album> albums) {
        accountsAdapter = new AlbumsAdapter(context, albums);

        binding.rvAlbums.setLayoutManager(new LinearLayoutManager(context));
        binding.rvAlbums.setAdapter(accountsAdapter);
        binding.rvAlbums.setItemAnimator(new DefaultItemAnimator());
        binding.rvAlbums.setNestedScrollingEnabled(true);
        accountsAdapter.notifyDataSetChanged();
    }
}
