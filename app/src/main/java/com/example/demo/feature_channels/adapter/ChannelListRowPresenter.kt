package com.example.demo.feature_channels

import android.view.View
import android.view.ViewGroup
import androidx.leanback.widget.*

class ChannelHeaderPresenter : RowHeaderPresenter() {
    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        return ViewHolder(View(parent.context))
    }
}

class ChannelListRowPresenter : ListRowPresenter() {
    init {
        headerPresenter = ChannelHeaderPresenter()
    }

    override fun onBindRowViewHolder(holder: RowPresenter.ViewHolder, item: Any?) {
        super.onBindRowViewHolder(holder, item)

        val listRow = item as? ListRow ?: return

        if (listRow.headerItem.name.contains("A")) {
            holder.view.layoutParams.height = 400
        }
    }
}