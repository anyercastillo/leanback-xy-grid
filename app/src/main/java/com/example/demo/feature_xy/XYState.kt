package com.example.demo.feature_xy

import com.example.demo.network.Channel

/**
 * Generic class to host the UI state for:
 * - items of type X on the x-axis,
 * - items of type Y on the y-axis
 *
 * https://docs.google.com/presentation/d/1X3m-n5EYA4ECksiv3IWor0TfWQz2VHZ46pJMp2MmOF0
 */
data class XYState(
    val channelBeforeList: List<Channel>,
    val channel: Channel?,
    val channelAfterList: List<Channel>,
    val listingBeforeList: List<String>,
    val listing: String?,
    val listingAfterList: List<String>
) {
    val formattedBeforeX: String = listingBeforeList.lastOrNull()?.toString() ?: "<EMPTY>"
    val formattedCardX: String = listing.orEmpty()

    val formattedBeforeY: String = channelBeforeList.lastOrNull()?.id?.toString() ?: "<EMPTY>"
    val formattedCardY: String = channel?.channelName.orEmpty()


    val formattedDebugYBefore: String =
        "X before-list = ${channelBeforeList.joinToString(", ") { it.id.toString() }}"
    val formattedDebugY: String = "X = ${channel?.channelName}"
    val formattedDebugYAfter: String =
        "X after-list = ${channelAfterList.joinToString(", ") { it.id.toString() }}"

    val formattedDebugXBefore: String =
        "Y before-list = ${listingBeforeList.joinToString(", ")}"
    val formattedDebugX: String = "X = $listing"
    val formattedDebugXAfter: String =
        "X after-list = ${listingAfterList.joinToString(", ")}"

}
