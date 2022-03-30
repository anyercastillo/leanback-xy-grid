package com.example.demo.feature_xy.utils

import android.view.KeyEvent


class KeyEventHandler {
    fun onKeyEvent(
        keyEvent: KeyEvent,
        xPivotList: PivotList<*>,
        yPivotList: PivotList<*>
    ): Boolean {
        if (keyEvent.action != KeyEvent.ACTION_DOWN) return false

        return when (keyEvent.keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                xPivotList.collect()
            }

            KeyEvent.KEYCODE_DPAD_LEFT -> {
                xPivotList.rollback()
            }

            KeyEvent.KEYCODE_DPAD_DOWN -> {
                yPivotList.collect()
            }

            KeyEvent.KEYCODE_DPAD_UP -> {
                yPivotList.rollback()
            }

            else -> false
        }
    }
}