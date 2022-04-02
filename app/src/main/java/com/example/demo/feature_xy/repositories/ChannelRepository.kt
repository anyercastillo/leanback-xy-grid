package com.example.demo.feature_xy.repositories

import com.example.demo.network.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun getChannels(): Flow<List<Channel>>
}
