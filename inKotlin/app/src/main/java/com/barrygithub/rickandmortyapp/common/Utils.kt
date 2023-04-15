package com.barrygithub.rickandmortyapp.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.barrygithub.rickandmortyapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Project RickAndMortyApp
 * Created by Barry Zea H. on 14/04/23.
 * Copyright (c) Barry Zea H. All rights reserved.
 *
 **/
fun ImageView.loadUrl(url:String,placeholder:Drawable) = Glide.with(this)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .placeholder(placeholder)
    .error(R.drawable.placeholder_error_image)
    .centerCrop()
    .into(this)
fun ImageView.loadUrl(url:String) = Glide.with(this)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .placeholder(R.drawable.placeholder_error_image)
    .error(R.drawable.placeholder_error_image)
    .centerCrop()
    .into(this)
