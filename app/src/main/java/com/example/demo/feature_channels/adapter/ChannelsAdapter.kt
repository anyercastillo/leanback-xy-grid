package com.example.demo.feature_channels.adapter

import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DiffCallback
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.example.demo.feature_channels.ChannelListRowPresenter
import com.example.demo.feature_channels.models.Channel
import com.example.demo.feature_channels.models.Listing

private val channelListRowDiffCallback = object : DiffCallback<ListRow>() {
    override fun areItemsTheSame(oldItem: ListRow, newItem: ListRow): Boolean {
        return oldItem.headerItem.name == newItem.headerItem.name
    }

    override fun areContentsTheSame(oldItem: ListRow, newItem: ListRow): Boolean {
        return oldItem.equals(newItem)
    }
}

private val listingDiffCallback = object : DiffCallback<Listing>() {
    override fun areItemsTheSame(oldItem: Listing, newItem: Listing): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Listing, newItem: Listing): Boolean {
        return oldItem == newItem
    }
}

private val initialList = listOf(
    Channel(
        name = "Channel A",
        listings = listOf(Listing("A1"), Listing("A2"), Listing("A3"))
    ),

    Channel(
        name = "Channel B",
        listings = listOf(Listing("B1"), Listing("B2"), Listing("B3"), Listing("B4"), Listing("B5"))
    ),

    Channel(
        name = "Channel C",
        listings = listOf(Listing("C1"), Listing("C2"), Listing("C3"), Listing("C4"))
    ),
)

class ChannelsAdapter : ArrayObjectAdapter(ChannelListRowPresenter()) {
    private val listingsHashMap = HashMap<String, ArrayObjectAdapter>()

    init {
        submitList(initialList)
    }

    private fun submitList(channels: List<Channel>) {
        val channelsListRows = mutableListOf<ListRow>()

        channels.forEach { channel ->
            if (!listingsHashMap.containsKey(channel.name)) {
                listingsHashMap[channel.name] = ArrayObjectAdapter(ListingPresenter())
            }

            val listingAdapter = listingsHashMap[channel.name] ?: return@forEach
            listingAdapter.setItems(channel.listings, listingDiffCallback)

            channelsListRows.add(
                ListRow(
                    HeaderItem(channel.name),
                    listingsHashMap[channel.name]
                )
            )
        }

        setItems(channelsListRows, channelListRowDiffCallback)
    }
}
