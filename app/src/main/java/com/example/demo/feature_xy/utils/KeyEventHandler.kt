package com.example.demo.feature_xy.utils

import android.view.KeyEvent

class KeyEventHandler {
    fun onKeyEvent(
        keyEvent: KeyEvent,
        onPivotListXCollect: () -> Boolean,
        onPivotListXRollback: () -> Boolean,
        onPivotListYCollect: () -> Boolean,
        onPivotListYRollback: () -> Boolean,
    ): Boolean {
        if (keyEvent.action != KeyEvent.ACTION_DOWN) return false

        return when (keyEvent.keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                onPivotListXCollect()
            }

            KeyEvent.KEYCODE_DPAD_LEFT -> {
                onPivotListXRollback()
            }

            KeyEvent.KEYCODE_DPAD_DOWN -> {
                onPivotListYCollect()
            }

            KeyEvent.KEYCODE_DPAD_UP -> {
                onPivotListYRollback()
            }

            else -> false
        }
    }
}