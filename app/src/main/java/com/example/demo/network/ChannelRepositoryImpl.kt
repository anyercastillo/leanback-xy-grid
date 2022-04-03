package com.example.demo.network

import com.example.demo.feature_xy.repositories.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChannelRepositoryImpl : ChannelRepository {
    override fun getChannels(): Flow<List<Channel>> = flow {
        emit(NetworkData.service.getChannels().channels)
    }

    override fun getListings(channel: Channel): List<String> = listOf("1", "2", "3", "4", "5")
}