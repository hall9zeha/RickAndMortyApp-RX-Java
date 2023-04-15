package com.barrygithub.rickandmortyapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.barrygithub.rickandmortyapp.R;
import com.barrygithub.rickandmortyapp.common.MyListener;
import com.barrygithub.rickandmortyapp.data.LocalDatasource.entities.Character;
import com.barrygithub.rickandmortyapp.databinding.ImageItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 3/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 **/
public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Character> imagesList = new ArrayList<>();
    private final int LOADING_ITEM=1;
    private final int IMAGE_ITEM=2;
    private Boolean isLoadingAdded=false;
    private static MyListener listener;

    public ImagesAdapter(MyListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
        View itemLoading = LayoutInflater.from(context).inflate(R.layout.item_loading,parent,false);

        if (viewType == LOADING_ITEM)return new ViewHolderLoading(itemLoading);
        return new ViewHolder(itemView);

    }

    @Override
    public int getItemViewType(int position) {
        return (position==imagesList.size() -1 && isLoadingAdded )? LOADING_ITEM: IMAGE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType()==IMAGE_ITEM) {
            ((ViewHolder) holder).onBind(imagesList.get(position));
        }

    }
    public void addAll(List<Character> characterApis){
       for(Character c: characterApis){
           if(!imagesList.contains(c)){
               imagesList.add(c);
               notifyItemInserted(imagesList.size()-1);
           }
       }
    }
    @Override
    public int getItemCount() {
        return imagesList.isEmpty() ? 0 : imagesList.size();
    }
    public void addLoadingFooter(){
        isLoadingAdded=true;
    }
    public void removeLoadingFooter(){
        isLoadingAdded=false;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageItemBinding bind;
        private Context ctx;
        public  ViewHolder(View itemView){
            super(itemView);
            bind=ImageItemBinding.bind(itemView);
            ctx=bind.getRoot().getContext();
        }
        public  void onBind(Character characterApi){
            CircularProgressDrawable placeholderLoading = new CircularProgressDrawable(ctx);
            placeholderLoading.setStrokeWidth(5f);
            placeholderLoading.setCenterRadius(30f);
            placeholderLoading.start();

            Glide.with(ctx)
                    .load(characterApi.getImage())
                    .centerCrop()
                    .placeholder(placeholderLoading)
                    .error(R.drawable.placeholder_error_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bind.ivMain);
            bind.tvName.setText(characterApi.getName());
            bind.getRoot().setOnClickListener(v->{ImagesAdapter.listener.onClick(characterApi);});
        }
    }
    public static  class ViewHolderLoading extends RecyclerView.ViewHolder {
        ViewHolderLoading(View itemView){
            super(itemView);
        }
    }
}
