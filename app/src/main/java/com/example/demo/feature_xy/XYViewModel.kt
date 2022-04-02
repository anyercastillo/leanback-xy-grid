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
import kotlinx.coroutines.launch

private val initialYList = listOf("1", "2", "3", "4", "5", "6", "7")

private val initialState = XYState<Channel, String>(
    beforeX = emptyList(),
    afterX = emptyList(),
    x = null,
    beforeY = emptyList(),
    afterY = emptyList(),
    y = ""
)

private val repository = ChannelRepositoryImpl()
private val keyEventHandler = KeyEventHandler()


class XYViewModel : ViewModel() {
    private val xPivotList = PivotList(emptyList<Channel>())
    private val yPivotList = PivotList(initialYList)
    private val keyEventHandler = KeyEventHandler()

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            channelsFlow.collect { channels ->
                val pivotList = PivotList(
                    list = channels,
                    onDataChangedListener = { before, pivot, after ->
                        pivotListXChanged(before, pivot, after)
                    }
                )

                val newState = state.value.copy(
                    isLoading = false,
                    pivotListX = pivotList
                )
                _state.tryEmit(newState)
            }
        }

        yOnPivotListChanged()

        viewModelScope.launch {
            xPivotList.setOnDataChangeListener {
                pivotListXChanged()
            }
        }

        viewModelScope.launch {
            yPivotList.setOnDataChangeListener {
                yOnPivotListChanged()
            }
        }

        viewModelScope.launch {
            val channels = repository.getChannels()

        }
    }

    fun onKeyEvent(keyEvent: KeyEvent): Boolean {
        return keyEventHandler.onKeyEvent(
            keyEvent = keyEvent,
            xPivotList = xPivotList,
            yPivotList = yPivotList
        )
    }

    private fun pivotListXChanged(before: List<Channel>, pivot: Channel, after: List<Channel>) {
        val newState = state.value.copy(
            beforeX = before,
            afterX = after,
            x = pivot
        )
        _state.tryEmit(newState)
    }

    private fun yOnPivotListChanged() {
        val newState = state.value.copy(
            beforeY = yPivotList.before,
            afterY = yPivotList.after,
            y = yPivotList.pivot
        )
        _state.tryEmit(newState)
    }
}