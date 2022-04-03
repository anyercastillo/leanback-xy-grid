package com.example.demo.feature_xy.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.example.demo.R
import com.example.demo.network.Channel

class ChannelDiffCallback : DiffUtil.ItemCallback<Channel>() {
    override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
        return oldItem == newItem
    }
}

class ChannelListViewHolder(val view: View) : XYViewHolder<Channel>(view) {
    override fun bindTo(item: Channel) {
        view.findViewById<TextView>(R.id.xy_item_y_text).apply {
            text = item.id.toString()
            setTextColor(Color.WHITE)
        }
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
