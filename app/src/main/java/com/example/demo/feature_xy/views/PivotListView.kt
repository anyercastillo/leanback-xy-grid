package com.example.demo.feature_xy.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout

private enum class AnimationType {
    NONE, COLLECT, ROLLBACK
}

abstract class PivotListView<T>(
    ctx: Context,
    attrs: AttributeSet,
    private val viewHolderFactory: ViewHolderFactory<T>
) : LinearLayout(ctx, attrs) {
    interface ViewHolderFactory<T> {
        fun createViewHolder(parent: ViewGroup): ViewHolder<T>
    }

    abstract class ViewHolder<T>(val view: View) {
        abstract fun bind(item: T)
    }

    private var lastKnownTag: Any? = null
    private var lastKnownItems: List<T>? = null

    private var viewHolders = listOf<ViewHolder<T>>()

    private val linearLayout = LinearLayout(context).apply {
        orientation = this@PivotListView.orientation
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        isFocusable = false
        isFocusableInTouchMode = false
    }
    private val linearLayoutAnimator = linearLayout.animate()

    abstract val numberOfViews: Int
    abstract val space: Int
    open val animationDuration: Long = 120

    private fun createViewHolders(): List<ViewHolder<T>> {
        return (0 until numberOfViews).map {
            val viewHolder = viewHolderFactory.createViewHolder(linearLayout)

            viewHolder.view.apply {
                visibility = View.INVISIBLE
            }

            return@map viewHolder
        }
    }

    /**
     * Whe a list of items is submitted we need to consider the follow:
     * - Stop any running animation [linearLayoutAnimator]
     * - Reset the [linearLayout] position to (0,0)
     * - Set views to [View.VISIBLE] only if they are presented to the screen
     *
     * If it is NOT the same tag: (different set of items, cannot be animated)
     *  - rebind all [viewHolders]
     *
     * If it is the same tag: (a variation of the previous list)
     *  - resolve animation (rollback / collect)
     *  rollback:
     *        last known list: [B, C, D]
     *               new list: [A, B, C]
     *    - produce list: [A, B, C, D]
     *    - animate entrance of <A>
     *  collect:
     *       last known list: [A, B, C]
     *              new list: [B, C, D]
     *    - produce list: [A, B, C, D]
     *    - animate entrance of <D>
     *
     * Update last known values for:
     *  - tag
     *  - items
     */
    fun submitList(tag: Any?, items: List<T>) {
        initializeIfNeeded()

        linearLayoutAnimator.cancel()
        linearLayout.x = 0f
        linearLayout.y = 0f

        if (requiresAnimation(tag, items)) {
            when (resolveAnimationType(items)) {
                AnimationType.NONE -> updateItemsWithNoAnimation(items)

                AnimationType.COLLECT -> {
                    val oldItems = lastKnownItems ?: return

                    if (items.isNotEmpty()) {
                        val animationItems = oldItems + items.first()
                        updateItemsWithCollectAnimation(animationItems)
                    } else {
                        updateItemsWithCollectAnimation(oldItems)
                    }
                }

                AnimationType.ROLLBACK -> {
                    val oldItems = lastKnownItems ?: return

                    if (oldItems.isNotEmpty()) {
                        val animationItems = listOf(oldItems.last()) + items
                        updateItemsWithRollbackAnimation(animationItems)
                    } else {
                        updateItemsWithRollbackAnimation(items)
                    }
                }
            }
        } else {
            updateItemsWithNoAnimation(items)
        }

        lastKnownTag = tag
        lastKnownItems = items
    }

    private fun updateItemsWithNoAnimation(items: List<T>) {
        viewHolders.forEachIndexed { index, viewHolder ->
            val view = viewHolder.view

            if (index < items.size) {
                view.visibility = VISIBLE
                viewHolder.bind(items[index])
            } else {
                view.visibility = INVISIBLE
            }
        }
    }

    /**
     * Resolves the animation.
     * If required data for animation cannot be resolved
     * then this function returns [AnimationType.NONE]
     */

    private fun resolveAnimationType(items: List<T>): AnimationType {
        val oldItems = lastKnownItems ?: return AnimationType.NONE

        if (oldItems.size < items.size) return AnimationType.ROLLBACK
        if (oldItems.size > items.size) return AnimationType.COLLECT

        // <At this point both lists have the same amount of items>

        if (oldItems.size > 1) {
            // <At this point both lists have more than one item>

            val oldFirstItem = oldItems.first()
            val newSecondItem = items[1]

            // Items are moving from left to right
            if (oldFirstItem == newSecondItem) return AnimationType.ROLLBACK

            // Items are moving from right to left
            return AnimationType.COLLECT
        }

        return AnimationType.NONE
    }

    private fun resolveAnimationType2(items: List<T>): AnimationType {
        val oldItems = lastKnownItems ?: return AnimationType.NONE

        if (items.isEmpty()) return AnimationType.COLLECT
        if (oldItems.isEmpty()) return AnimationType.ROLLBACK

        // <At this point both lists are NOT empty>

        // If both lists have only one item then it is not possible to
        // resolve the animation's direction
        if (oldItems.size < 2 && items.size < 2) return AnimationType.NONE

        // <At this point both lists have more than one item>

        val oldFirstItem = oldItems.first()
        val newSecondItem = items[1]

        // Items are moving from left to right
        if (oldFirstItem == newSecondItem) return AnimationType.ROLLBACK

        // Items are moving from right to left
        return AnimationType.COLLECT
    }

    private fun requiresAnimation(tag: Any?, items: List<T>): Boolean {
        if (lastKnownItems == items) return false
        if (tag == null) return false
        if (tag != lastKnownTag) return false

        if (lastKnownItems == null) return false

        return true
    }

    private fun updateItemsWithCollectAnimation(items: List<T>) {
        updateItemsWithNoAnimation(items)

        if (orientation == VERTICAL) {
            linearLayout.translationY = 0f
            linearLayoutAnimator
                .translationY(-space.toFloat())
                .setDuration(animationDuration)
                .start()
        } else {
            linearLayout.translationX = 0f
            linearLayoutAnimator
                .translationX(-space.toFloat())
                .setDuration(animationDuration)
                .start()
        }
    }

    private fun updateItemsWithRollbackAnimation(items: List<T>) {
        updateItemsWithNoAnimation(items)

        if (orientation == VERTICAL) {
            linearLayout.translationY = -space.toFloat()
            linearLayoutAnimator
                .translationY(0f)
                .setDuration(animationDuration)
                .start()
        } else {
            linearLayout.translationX = -space.toFloat()
            linearLayoutAnimator
                .translationX(0f)
                .setDuration(animationDuration)
                .start()
        }
    }

    private fun initializeIfNeeded() {
        if (viewHolders.isNotEmpty()) return

        viewHolders = createViewHolders()

        viewHolders.forEach { viewHolder ->
            linearLayout.addView(viewHolder.view)
        }

        addView(linearLayout)
    }
}