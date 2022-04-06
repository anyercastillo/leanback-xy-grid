package com.example.demo.feature_xy.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.feature_xy.utils.convertDpToPixel
import com.example.demo.network.Listing


class ListingViewHolder(view: View) : PivotListView.ViewHolder<Listing>(view) {
    private val imageView = view.findViewById<AppCompatImageView>(R.id.xy_item_x_image)

    override fun bind(item: Listing) {
        Glide.with(view.context)
            .load(item.resolveFilePathThumbUrl())
            .into(imageView)
    }
}

class ListingListViewHolderFactory : PivotListView.ViewHolderFactory<Listing> {
    override fun createViewHolder(parent: ViewGroup): PivotListView.ViewHolder<Listing> {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_xy_item_x, parent, false)

        return ListingViewHolder(view)

    }
}

class ListingListView(
    ctx: Context,
    attrs: AttributeSet,
) : PivotListView<Listing>(ctx, attrs, ListingListViewHolderFactory()) {
    override val numberOfViews: Int = 7
    override val space: Int = ctx.convertDpToPixel(152)
}

