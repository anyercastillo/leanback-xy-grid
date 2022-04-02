package com.example.demo.feature_xy.utils

/**
 * https://docs.google.com/presentation/d/1X3m-n5EYA4ECksiv3IWor0TfWQz2VHZ46pJMp2MmOF0/edit?usp=sharing
 *
 */
class PivotList<T>(
    private val list: List<T>,
    private val onDataChangedListener: (
        before: List<T>,
        pivot: T,
        after: List<T>
    ) -> Unit = { _, _, _ -> }
) {
    init {
        if (list.isEmpty()) throw Exception("List cannot be empty.")
    }

    private var pivotIndex = 0

    /**
     * Represents all the items from the start of the list until the left of the pivot.
     *
     * Example:
     * list: [A B C D E F G H I]
     * pivot: E
     * returns: [A B C D]
     * +---------------------+
     * + A B C D <E> F G H I +
     * +---------------------+
     */
    val before: List<T>
        get() = resolveBefore()

    /**
     * Represents all the items from the left of the pivot until the end of the list.
     *
     * Example:
     * list: [A B C D E F G H I]
     * pivot: E
     * returns: [F G H I]
     * +---------------------+
     * + A B C D <E> F G H I +
     * +---------------------+
     */
    val after: List<T>
        get() = resolveAfter()

    /**
     * Represent the pivot in the list.
     *
     * Example:
     * list: [A B C D E F G H I]
     * pivot: E
     * +---------------------+
     * + A B C D <E> F G H I +
     * +---------------------+
     */
    val pivot: T
        get() = list[pivotIndex]

    /**
     * Collects an item from the after-list.
     *
     * If the pivot is at the last position of the list,
     * then it does nothing.
     *
     * Returns if operation happens.
     */
    fun collect(): Boolean {
        if (pivotIndex == list.size - 1) return false
        pivotIndex += 1

        onDataChangedListener.invoke(before, pivot, after)

        return true
    }

    /**
     * Collects an item from the before-list.
     *
     * If the pivot is at the first position of the list,
     * then it does nothing.
     *
     * Returns if operation happens.
     */
    fun rollback(): Boolean {
        if (pivotIndex == 0) return false
        pivotIndex -= 1

        onDataChangedListener.invoke(before, pivot, after)

        return true
    }

    /**
     * Resolves the before-list.
     *
     * If pivot is at first position of the list,
     * then it is nothing left on the before-list.
     *
     * Returns all the items from the start of the list until the left of the pivot.
     *
     * Example:
     * list: [A B C D E F G H I]
     * pivot: E
     * returns: [A B C D]
     * +---------------------+
     * + A B C D <E> F G H I +
     * +---------------------+
     */
    private fun resolveBefore(): List<T> {
        if (pivotIndex == 0) return emptyList()

        return list.subList(0, pivotIndex)
    }

    /**
     * Resolves the after list.
     *
     * If pivot is at last position of the list,
     * then it is nothing left on the after-list.
     *
     * Returns all the items from the right of the pivot until the end of the list.
     *
     * Example:
     * list: [A B C D E F G H I]
     * pivot: E
     * returns: [F G H I]
     * +---------------------+
     * + A B C D <E> F G H I +
     * +---------------------+
     */
    private fun resolveAfter(): List<T> {
        if (pivotIndex == list.size - 1) return emptyList()

        return list.subList(pivotIndex + 1, list.size)
    }
}
