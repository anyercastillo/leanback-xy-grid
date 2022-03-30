package com.example.demo.feature_xy

/**
 * Generic class to host the UI state for:
 * - items of type X on the x-axis,
 * - items of type Y on the y-axis
 *
 * https://docs.google.com/presentation/d/1X3m-n5EYA4ECksiv3IWor0TfWQz2VHZ46pJMp2MmOF0
 */
data class XYState<X, Y>(
    val beforeX: List<X>,
    val afterX: List<X>,
    val x: X,
    val beforeY: List<Y>,
    val afterY: List<Y>,
    val y: Y,
) {
    val formattedBeforeX: String = beforeX.lastOrNull()?.toString() ?: "<EMPTY>"
    val formattedBeforeY: String = beforeY.lastOrNull()?.toString() ?: "<EMPTY>"
    val formattedCardX: String = x.toString()
    val formattedCardY: String = y.toString()
    val formattedDebugXBefore: String = "X before-list = ${beforeX.joinToString(", ")}"
    val formattedDebugX: String = "X = $x"
    val formattedDebugXAfter: String = "X after-list = ${afterX.joinToString(", ")}"
    val formattedDebugYBefore: String = "Y before-list = ${beforeY.joinToString(", ")}"
    val formattedDebugY: String = "X = $y"
    val formattedDebugYAfter: String = "X after-list = ${afterY.joinToString(", ")}"

}
