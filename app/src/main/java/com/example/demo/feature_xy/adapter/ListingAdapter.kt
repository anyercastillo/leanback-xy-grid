package com.example.demo.feature_xy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.demo.R

class ListingDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class ListingListViewHolder(val view: View) : XYViewHolder<String>(view) {
    private val imageView = view.findViewById<AppCompatImageView>(R.id.xy_item_x_image)

    override fun bindTo(item: String) {
        Glide.with(view.context)
            .load(item)
            .into(imageView)
    }
}

class ListingListAdapter : XYAdapter<String, ListingListViewHolder>(ListingDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_xy_item_x, parent, false)
        return ListingListViewHolder(view)
    }
}
