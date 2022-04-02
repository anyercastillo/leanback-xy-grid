package com.example.demo.feature_xy.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.leanback.widget.HorizontalGridView
import androidx.recyclerview.widget.DefaultItemAnimator

fun animateViewWidth(
    view: View,
    currentWidth: Int,
    newWidth: Int,
    duration: Long,
    onAnimationStart: () -> Unit,
    onAnimationEnd: () -> Unit,
) {
    val slideAnimator = ValueAnimator
        .ofInt(currentWidth, newWidth)
        .setDuration(duration)

    /**
     *  We use an update listener which listens to each tick
     *  and manually updates the height of the view
     */
    slideAnimator.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        view.x = value.toFloat()
        view.requestLayout()
    }

    //  We use an animationSet to play the animation
    val animatorSet = AnimatorSet()
    animatorSet.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animator: Animator) {
            onAnimationStart.invoke()
        }

        override fun onAnimationEnd(animator: Animator) {
            onAnimationEnd.invoke()
        }
    })

    animatorSet.interpolator = ValueAnimator().interpolator
    animatorSet.play(slideAnimator)
    animatorSet.start()
}

class FooHorizontalGridView(
    ctx: Context,
    attrs: AttributeSet
) : HorizontalGridView(ctx, attrs) {
    init {
        setupItemAnimator()
//        itemAnimator = null
    }

    private fun setupItemAnimator() {
        itemAnimator = object : DefaultItemAnimator() {
            private val pendingRemovals = ArrayList<ViewHolder>()
            private val pendingAdds = ArrayList<ViewHolder>()

            init {
                removeDuration = 2000
                addDuration = 2000
                moveDuration = 2000
            }

            override fun animateAdd(holder: ViewHolder): Boolean {
                pendingAdds.add(holder)
                return true
            }

            override fun animateRemove(holder: ViewHolder): Boolean {
                pendingRemovals.add(holder)
                return true
            }

            override fun runPendingAnimations() {
                for (holder in pendingRemovals) {
                    animateRemoveImpl(holder)
                }
                pendingRemovals.clear()

                for (holder in pendingAdds) {
                    animateAddImpl(holder)
                }
                pendingAdds.clear()

                super.runPendingAnimations()
            }

            private fun animateRemoveImpl(holder: ViewHolder) {
                val view = holder.itemView

                animateViewWidth(
                    view = view,
                    currentWidth = 0,
                    newWidth = -200,
                    duration = removeDuration,
                    onAnimationStart = {
                        dispatchRemoveStarting(holder)
                    },
                    onAnimationEnd = {
                        dispatchRemoveFinished(holder)
                        dispatchFinishedWhenDone()
                    }
                )
            }

            fun dispatchFinishedWhenDone() {
                if (!isRunning) {
                    dispatchAnimationsFinished()
                }
            }

            private fun animateAddImpl(holder: ViewHolder) {
                val view = holder.itemView

                animateViewWidth(
                    view = view,
                    currentWidth = -200,
                    newWidth = 0,
                    duration = addDuration,
                    onAnimationStart = {
                        dispatchAddStarting(holder)
                    },
                    onAnimationEnd = {
                        dispatchAddFinished(holder)
                        dispatchFinishedWhenDone()
                    }
                )
            }
        }
    }
}