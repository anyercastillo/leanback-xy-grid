package com.example.demo.feature_channels.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.Presenter
import com.example.demo.feature_channels.models.Listing

class ListingPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val textView = TextView(parent.context)
        textView.apply {
            isFocusable = true
            isFocusableInTouchMode = true
        }
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val textView = viewHolder.view as TextView
        val listing = item as Listing
        textView.text = listing.title
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}