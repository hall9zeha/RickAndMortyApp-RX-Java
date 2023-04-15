package com.barrygithub.rickandmortyapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.barrygithub.rickandmortyapp.R
import com.barrygithub.rickandmortyapp.common.loadUrl
import com.barrygithub.rickandmortyapp.data.localDatasource.Character
import com.barrygithub.rickandmortyapp.databinding.ImageItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/

class ImagesAdapter(private val listener:(Character)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val imagesList = ArrayList<Character>()
    private val LOADING_ITEM = 1
    private val IMAGE_ITEM = 2
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        val itemLoading: View =
            LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
        return if (viewType == LOADING_ITEM) ViewHolderLoading(itemLoading) else ViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == imagesList.size - 1 && isLoadingAdded) LOADING_ITEM else IMAGE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == IMAGE_ITEM) {
            (holder as ViewHolder).onBind(imagesList[position])
        }
    }

    fun addAll(characterApi: List<Character>) {
        characterApi.forEach {c->
            if (!imagesList.contains(c)) {
                imagesList.add(c)
                notifyItemInserted(imagesList.size - 1)
            }
        }

    }

    override fun getItemCount(): Int {
        return if (imagesList.isEmpty()) 0 else imagesList.size
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bind: ImageItemBinding
        private val ctx: Context

        init {
            bind = ImageItemBinding.bind(itemView)
            ctx = bind.root.context
        }

        fun onBind(character: Character) = with(bind) {
            val placeholderLoading = CircularProgressDrawable(ctx)
            placeholderLoading.strokeWidth = 5f
            placeholderLoading.centerRadius = 30f
            placeholderLoading.start()
            ivMain.loadUrl(character.image,placeholderLoading)

            tvName.text = character.name
            root.setOnClickListener {
               listener(character)
            }
        }
    }

    inner class ViewHolderLoading internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)


}
