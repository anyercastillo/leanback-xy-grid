import android.view.View
import android.view.ViewPropertyAnimator
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView

/**
 * Defines an [RecyclerView.ItemAnimator] which the following characteristics:
 * - removal: slides the item up
 * - addition: slides the item down
 *
 * This item animator is suitable for a list that looks like a stack, where items are removed
 * and added only from the top.
 *
 * Example:
 * +---+      Original list.
 * + A +      Operations are always performed in the first item.
 * + B +
 * + C +
 * + D +
 * +---+
 *
 * Operation Remove:
 *
 * + A +
 * +---+      Item <A> is moved up,
 * + B +      until it is out of the list.
 * + C +
 * + D +
 * +---+
 *
 * Operation Add:
 * +---+      Item <A> is moved down,
 * + A +      until it is in the list.
 * + B +
 * + C +
 * + D +
 * +---+
 */
class VerticalStackItemAnimator : AddRemoveItemAnimator() {
    /**
     * Slides up the given [view]
     *
     * The view is moved up the same amount of vertical pixels taken by the view.
     */
    override fun resolveRemoveAnimator(view: View): ViewPropertyAnimator {
        val height = view.height
        val margins = view.marginTop + view.marginBottom
        val space = height + margins
        val negativeSpace = -1 * space
        return view.animate().translationY(negativeSpace.toFloat())
    }

    /**
     * Slides to the right the given [view]
     *
     * The view is moved down until its Y reaches 0.
     */
    override fun resolveAddAnimator(view: View): ViewPropertyAnimator {
        return view.animate().translationY(0f)
    }
}