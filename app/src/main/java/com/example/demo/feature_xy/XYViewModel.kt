package com.example.demo.feature_xy

import android.view.KeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.feature_xy.utils.KeyEventHandler
import com.example.demo.feature_xy.utils.PivotList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val initialXList = listOf("A", "B", "C", "D")
private val initialYList = listOf("1", "2", "3", "4", "5", "6", "7")

private val initialState = XYState(
    beforeX = emptyList(),
    afterX = emptyList(),
    x = "",
    beforeY = emptyList(),
    afterY = emptyList(),
    y = ""
)

class XYViewModel : ViewModel() {
    private val xPivotList = PivotList(initialXList)
    private val yPivotList = PivotList(initialYList)
    private val keyEventHandler = KeyEventHandler()

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    init {
        xOnPivotListChanged()
        yOnPivotListChanged()

        viewModelScope.launch {
            xPivotList.setOnDataChangeListener {
                xOnPivotListChanged()
            }
        }

        viewModelScope.launch {
            yPivotList.setOnDataChangeListener {
                yOnPivotListChanged()
            }
        }
    }

    fun onKeyEvent(keyEvent: KeyEvent): Boolean {
        return keyEventHandler.onKeyEvent(
            keyEvent = keyEvent,
            xPivotList = xPivotList,
            yPivotList = yPivotList
        )
    }

    private fun xOnPivotListChanged() {
        val newState = state.value.copy(
            beforeX = xPivotList.before,
            afterX = xPivotList.after,
            x = xPivotList.pivot
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