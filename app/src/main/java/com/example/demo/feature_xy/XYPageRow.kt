package com.example.demo.feature_xy

import android.view.View
import androidx.fragment.app.Fragment
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import com.example.demo.MainFragment
import com.example.demo.R

/**
 * Defines the [PageRow] to be use in [MainFragment]
 */
class XYPageRow : PageRow(HeaderItem(100, "XY Layout"))

/**
 * Provides a Factory for the [BrowseSupportFragment] architecture to
 * create the [XYPageRowFragment] and make it available as a row in the list of rows.
 */
class XYPageRowFragmentFactory :
    BrowseSupportFragment.FragmentFactory<XYPageRowFragment>() {
    override fun createFragment(row: Any): XYPageRowFragment {
        return XYPageRowFragment()
    }
}

/**
 * Provides an Adapter for the [BrowseSupportFragment] architecture to
 * allow communication between the [BrowseSupportFragment] and the fragment.
 */
class XYPageRowFragmentAdapter(fragment: XYPageRowFragment) :
    BrowseSupportFragment.MainFragmentAdapter<XYPageRowFragment>(fragment) {

    init {
        isScalingEnabled = false
    }

    override fun setEntranceTransitionState(state: Boolean) {
        super.setEntranceTransitionState(state)

        fragment.setEntranceTransitionState(state)
    }
}

/**
 * Defines the fragment used as entry point for the feature.
 */
class XYPageRowFragment : Fragment(R.layout.fragment_xy_page_row),
    BrowseSupportFragment.MainFragmentAdapterProvider {

    private val fragmentAdapter by lazy { XYPageRowFragmentAdapter(this) }

    override fun getMainFragmentAdapter(): BrowseSupportFragment.MainFragmentAdapter<*> {
        return fragmentAdapter
    }

    fun setEntranceTransitionState(state: Boolean) {
        requireView().visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}