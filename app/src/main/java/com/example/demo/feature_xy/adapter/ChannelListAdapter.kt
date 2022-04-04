package com.example.demo.feature_xy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.network.Channel

class ChannelDiffCallback : DiffUtil.ItemCallback<Channel>() {
    override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
        return oldItem.id == newItem.id
    }
}

class ChannelListViewHolder(val view: View) : XYViewHolder<Channel>(view) {
    private val imageView = view.findViewById<AppCompatImageView>(R.id.xy_item_y_image)
    private val textView = view.findViewById<TextView>(R.id.xy_item_y_text)

    override fun bindTo(item: Channel) {
        textView.apply {
            text = item.channelName
        }

        Glide.with(view.context)
            .load(item.resolveFilePathSmallLogoUrl())
            .fitCenter()
            .into(imageView)
    }
}

class ChannelListAdapter : XYAdapter<Channel, ChannelListViewHolder>(ChannelDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_xy_item_y, parent, false)
        return ChannelListViewHolder(view)
    }
}
