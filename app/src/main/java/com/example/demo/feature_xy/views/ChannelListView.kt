package com.example.demo.feature_xy.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.feature_xy.utils.convertDpToPixel
import com.example.demo.network.Channel


class ChannelViewHolder(view: View) : PivotListView.ViewHolder<Channel>(view) {
    private val imageView = view.findViewById<AppCompatImageView>(R.id.xy_item_y_image)
    private val textView = view.findViewById<TextView>(R.id.xy_item_y_text)

    override fun bind(item: Channel) {
        textView.apply {
            text = item.channelName
        }

        Glide.with(view.context)
            .load(item.resolveFilePathSmallLogoUrl())
            .into(imageView)
    }
}

class ChannelListViewHolderFactory : PivotListView.ViewHolderFactory<Channel> {
    override fun createViewHolder(parent: ViewGroup): PivotListView.ViewHolder<Channel> {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_xy_item_y, parent, false)

        return ChannelViewHolder(view)

    }
}

class ChannelListView(
    ctx: Context,
    attrs: AttributeSet,
) : PivotListView<Channel>(ctx, attrs, ChannelListViewHolderFactory()) {
    override val numberOfViews: Int = 5
    override val space: Int = ctx.convertDpToPixel(44)
}

