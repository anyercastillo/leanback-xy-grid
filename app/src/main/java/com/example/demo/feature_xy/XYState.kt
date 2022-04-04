package com.example.demo.feature_xy

import com.example.demo.network.Channel
import com.example.demo.network.Listing

/**
 * Generic class to host the UI state for:
 * - items of type X on the x-axis,
 * - items of type Y on the y-axis
 *
 * https://docs.google.com/presentation/d/1X3m-n5EYA4ECksiv3IWor0TfWQz2VHZ46pJMp2MmOF0
 */
data class XYState(
    val channelBefore: Channel?,
    val channel: Channel?,
    val channelAfterList: List<Channel>,
    val listingBefore: Listing?,
    val listing: Listing?,
    val listingAfterList: List<Listing>
) {
    val beforeListingImage: String? = listingBefore?.resolveFilePathThumbUrl()

    val beforeChannelIcon: String? = channelBefore?.resolveFilePathSmallLogoUrl()
    val beforeChannelText: String? = channelBefore?.channelName

    val cardIcon: String? = channel?.resolveFilePathLogoSelectedUrl()
    val cardImage: String? = listing?.resolveFilePathThumbUrl()
    val cardTitle: String? = listing?.title
    val cardDescription: String? = listing?.description
}
