package com.andreydubovets.app.imagelist

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.andreydubovets.testapp.R
import com.bumptech.glide.Glide

class ImageListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val imageView: ImageView = view.findViewById(R.id.imageview)

    fun bind(uri: Uri) {
        Glide.with(view.context)
                .load(uri)
                .into(imageView)
    }

}