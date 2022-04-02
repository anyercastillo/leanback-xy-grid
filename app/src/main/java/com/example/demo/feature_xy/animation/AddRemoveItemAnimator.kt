import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Defines a [RecyclerView.ItemAnimator] with logic related to only handle:
 * - removals
 * - additions
 *
 * Class that extends this class should provide custom implementation for:
 * - removal animator [resolveRemoveAnimator]
 * - addition animator [resolveAddAnimator]
 */
abstract class AddRemoveItemAnimator : DefaultItemAnimator() {
    private val pendingRemovals = ArrayList<RecyclerView.ViewHolder>()
    private val pendingAdds = ArrayList<RecyclerView.ViewHolder>()

    init {
        changeDuration = 0
        removeDuration = moveDuration
        addDuration = moveDuration
    }

    abstract fun resolveRemoveAnimator(view: View): ViewPropertyAnimator
    abstract fun resolveAddAnimator(view: View): ViewPropertyAnimator

    /**
     * Tracks the given [holder] for future removal.
     */
    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        pendingRemovals.add(holder)
        return true
    }

    /**
     * Tracks the given [holder] for future addition.
     */
    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        pendingAdds.add(holder)
        return true
    }

    /**
     * Resolves any pending animation.
     *
     * Iterates over pending removals and additions.
     */
    override fun runPendingAnimations() {
        runRemovalsPendingAnimations()
        runAddsPendingAnimations()

        super.runPendingAnimations()
    }

    /**
     * Notifies any listeners that all pending and active item animations are finished.
     */
    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }

    /**
     * Executes all pending Removals.
     */
    private fun runRemovalsPendingAnimations() {
        for (holder in pendingRemovals) {
            animateRemoveImpl(holder)
        }
        pendingRemovals.clear()
    }

    /**
     * Animates the [holder] to be removed.
     */
    private fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        resolveRemoveAnimator(holder.itemView)
            .setDuration(removeDuration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animator: Animator) {
                        dispatchRemoveStarting(holder)
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        dispatchRemoveFinished(holder)
                        dispatchFinishedWhenDone()
                    }
                }
            )
            .start()
    }

    /**
     * Executes all pending Additions.
     */
    private fun runAddsPendingAnimations() {
        for (holder in pendingAdds) {
            animateAddImpl(holder)
        }
        pendingAdds.clear()
    }

    /**
     * Animates the [holder] to be added.
     */
    private fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        resolveAddAnimator(holder.itemView)
            .setDuration(removeDuration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animator: Animator) {
                        dispatchAddStarting(holder)
                    }

                    override fun onAnimationEnd(animator: Animator) {
                        dispatchAddFinished(holder)
                        dispatchFinishedWhenDone()
                    }
                }
            )
            .start()
    }
}