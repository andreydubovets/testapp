package com.andreydubovets.app.imagelist

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.andreydubovets.testapp.R

class ImageListAdapter(uris: List<Uri>): RecyclerView.Adapter<ImageListViewHolder>() {
    private val uriList = ArrayList<Uri>()

    init {
        setData(uris)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_image, parent, false)
        return ImageListViewHolder(view)
    }

    override fun getItemCount() = uriList.size

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bind(uriList[position])
    }

    fun setData(uris: List<Uri>) {
        uriList.clear()
        uriList.addAll(uris)
        notifyDataSetChanged()
    }
}