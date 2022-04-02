import android.view.View
import android.view.ViewPropertyAnimator
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView

/**
 * Defines an [RecyclerView.ItemAnimator] which the following characteristics:
 * - removal: slides the item to the left
 * - addition: slides the item to the right
 *
 * This item animator is suitable for a list that looks like a stack, where items are removed
 * and added only from the left.
 *
 * Example:
 * +-------------+      Original list.
 * + A B C D E F +      Operations are always performed in the first item.
 * +-------------+
 *
 * Operation Remove:
 *  ---+-----------+     Item <A> is moved from right to left,
 *   A + B C D E F +     until it is out of the list.
 *  ---+-----------+
 *
 * Operation Add:
 * +-------------+       Item <A> is moved from left to right,
 * + A B C D E F +       until it is in the list.
 * +-------------+
 */
class HorizontalStackItemAnimator : AddRemoveItemAnimator() {
    /**
     * Slides to the left the given [view]
     *
     * The view is moved to the left the same amount of horizontal pixels taken by the view.
     */
    override fun resolveRemoveAnimator(view: View): ViewPropertyAnimator {
        val width = view.width
        val margins = view.marginLeft + view.marginRight
        val space = width + margins
        val negativeSpace = -1 * space
        return view.animate().translationX(negativeSpace.toFloat())
    }

    /**
     * Slides to the right the given [view]
     *
     * The view is moved to the right until its X reaches 0.
     */
    override fun resolveAddAnimator(view: View): ViewPropertyAnimator {
        return view.animate().translationX(0f)
    }
}