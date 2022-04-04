package com.example.demo.network

import com.example.demo.feature_xy.repositories.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChannelRepositoryImpl : ChannelRepository {
    override fun getChannels(): Flow<List<Channel>> = flow {
        val channels = NetworkData.service.getChannels().channels
        val channelsWithListing = channels.map { channel ->
            val listings = NetworkData.service.getListings(channel.slug)
            channel.copy(listings = listings.listing)
        }
        val channelWithNonEmptyListings = channelsWithListing.filter { it.listings.isNotEmpty() }
        emit(channelWithNonEmptyListings)
    }
}