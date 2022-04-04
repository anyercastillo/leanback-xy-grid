package com.example.demo.network

import com.example.demo.feature_xy.repositories.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChannelRepositoryImpl : ChannelRepository {
    override fun getChannels(): Flow<List<Channel>> = flow {
        emit(NetworkData.service.getChannels().channels)
    }
}