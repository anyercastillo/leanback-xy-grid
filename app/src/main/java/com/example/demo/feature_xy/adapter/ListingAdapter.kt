package com.example.demo.feature_xy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.network.Listing

class ListingDiffCallback : DiffUtil.ItemCallback<Listing>() {
    override fun areItemsTheSame(oldItem: Listing, newItem: Listing): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Listing, newItem: Listing): Boolean {
        return oldItem.id == newItem.id
    }
}

class ListingListViewHolder(val view: View) : XYViewHolder<Listing>(view) {
    private val imageView = view.findViewById<AppCompatImageView>(R.id.xy_item_x_image)

    override fun bindTo(item: Listing) {
        Glide.with(view.context)
            .load(item.resolveFilePathThumbUrl())
            .into(imageView)
    }
}

class ListingListAdapter : XYAdapter<Listing, ListingListViewHolder>(ListingDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_xy_item_x, parent, false)
        return ListingListViewHolder(view)
    }
}
