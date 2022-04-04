package com.example.demo.feature_xy

import android.view.KeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.feature_xy.utils.KeyEventHandler
import com.example.demo.feature_xy.utils.PivotList
import com.example.demo.network.Channel
import com.example.demo.network.ChannelRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

private val initialState = XYState(
    channelBeforeList = emptyList(),
    channel = null,
    channelAfterList = emptyList(),
    listingBeforeList = emptyList(),
    listing = null,
    listingAfterList = emptyList()
)

private val repository = ChannelRepositoryImpl()
private val keyEventHandler = KeyEventHandler()

class XYViewModel : ViewModel() {
    private var runningChannelPivotList: PivotList<Channel>? = null

    // HashMap: Key: Channel.id, Value: PivotList<String>
    private val stateTracker = HashMap<Int, PivotList<String>>()

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val channelsFlow = repository.getChannels()

    init {
        viewModelScope.launch {
            channelsFlow
                .filter { it.isNotEmpty() }
                .collect { channels ->
                    runningChannelPivotList = PivotList(channels)
                    stateTracker.clear()
                    emitNewState()
                }
        }
    }

    fun onKeyEvent(keyEvent: KeyEvent): Boolean {
        val channelPivotList = runningChannelPivotList ?: return false
        val channelId = channelPivotList.pivot.id
        val listingPivotList = stateTracker[channelId] ?: return false

        val handled = keyEventHandler.onKeyEvent(
            keyEvent = keyEvent,
            onPivotListXCollect = { listingPivotList.collect() },
            onPivotListXRollback = { listingPivotList.rollback() },
            onPivotListYCollect = { channelPivotList.collect() },
            onPivotListYRollback = { channelPivotList.rollback() },
        )

        if (handled) {
            emitNewState()
        }

        return handled
    }

    private fun emitNewState() {
        val channelPivotList = runningChannelPivotList ?: return
        val channel = channelPivotList.pivot
        val channelId = channel.id
        val listings = channelPivotList.pivot.listingUrls()

        if (!stateTracker.containsKey(channelId)) {
            stateTracker[channelId] = PivotList(listings)
        }

        val listingPivotList = stateTracker[channelId] ?: return

        val newState = state.value.copy(
            channelBeforeList = channelPivotList.before,
            channel = channel,
            channelAfterList = channelPivotList.after,
            listingBeforeList = listingPivotList.before,
            listing = listingPivotList.pivot,
            listingAfterList = listingPivotList.after
        )

        _state.tryEmit(newState)
    }
}