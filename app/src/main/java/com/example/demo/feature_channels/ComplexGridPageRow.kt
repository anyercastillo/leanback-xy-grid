package com.example.demo.feature_channels

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
class ChannelsGridPageRow : PageRow(HeaderItem(100, "Channels Grid"))

/**
 * Provides a Factory for the [BrowseSupportFragment] architecture to
 * create the [ChannelsGridPageRowFragment] and make it available as a row in the list of rows.
 */
class GridPageRowFragmentFactory :
    BrowseSupportFragment.FragmentFactory<ChannelsGridPageRowFragment>() {
    override fun createFragment(row: Any): ChannelsGridPageRowFragment {
        return ChannelsGridPageRowFragment()
    }
}

/**
 * Provides an Adapter for the [BrowseSupportFragment] architecture to
 * allow communication between the [BrowseSupportFragment] and the fragment.
 */
class ChannelsGridPageRowFragmentAdapter(fragment: ChannelsGridPageRowFragment) :
    BrowseSupportFragment.MainFragmentAdapter<ChannelsGridPageRowFragment>(fragment) {

    init {
        isScalingEnabled = false
    }

    override fun setEntranceTransitionState(state: Boolean) {
        super.setEntranceTransitionState(state)

        fragment.setEntranceTransitionState(state)
    }
}

/**
 * Defines the fragment used as entry point for the Channels Grid feature.
 */
class ChannelsGridPageRowFragment : Fragment(R.layout.fragment_channels_grid_page_row),
    BrowseSupportFragment.MainFragmentAdapterProvider {

    private val fragmentAdapter by lazy { ChannelsGridPageRowFragmentAdapter(this) }

    override fun getMainFragmentAdapter(): BrowseSupportFragment.MainFragmentAdapter<*> {
        return fragmentAdapter
    }

    fun setEntranceTransitionState(state: Boolean) {
        requireView().visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}