package com.example.demo.feature_xy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.example.demo.R

class XDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class XListViewHolder(val view: View) : XYViewHolder<String>(view) {
    override fun bindTo(item: String) {
        view.findViewById<TextView>(R.id.xy_item_x_text).text = item
    }
}

class XListAdapter : XYAdapter<String, XListViewHolder>(XDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_xy_item_x, parent, false)
        return XListViewHolder(view)
    }
}
