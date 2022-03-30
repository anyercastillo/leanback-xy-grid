package com.example.demo.feature_xy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.example.demo.R

class YDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class YListViewHolder(val view: View) : XYViewHolder<String>(view) {
    override fun bindTo(item: String) {
        view.findViewById<TextView>(R.id.xy_item_y_text).text = item
    }
}

class YListAdapter : XYAdapter<String, YListViewHolder>(YDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_xy_item_y, parent, false)
        return YListViewHolder(view)
    }
}
