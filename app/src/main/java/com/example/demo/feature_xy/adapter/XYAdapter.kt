package com.example.demo.feature_xy.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class XYViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindTo(item: T)
}

abstract class XYAdapter<T, VH : XYViewHolder<T>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
    }
}
