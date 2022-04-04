package com.example.demo.feature_xy

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.leanback.widget.HorizontalGridView
import androidx.leanback.widget.VerticalGridView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.feature_xy.adapter.ChannelListAdapter
import com.example.demo.feature_xy.adapter.ListingListAdapter
import com.example.demo.feature_xy.utils.repeatOnLifecycleStarted
import com.example.demo.network.Channel
import com.example.demo.network.Listing
import kotlinx.coroutines.flow.collectLatest

class XYFragment : Fragment(R.layout.fragment_xy) {
    private lateinit var listingBeforeImage: AppCompatImageView
    private lateinit var listings: HorizontalGridView

    private lateinit var channelIcon: AppCompatImageView
    private lateinit var listingImage: AppCompatImageView
    private lateinit var listingTitle: AppCompatTextView
    private lateinit var listingDescription: AppCompatTextView

    private lateinit var channelBeforeIcon: AppCompatImageView
    private lateinit var channelBeforeText: AppCompatTextView
    private lateinit var channels: VerticalGridView

    private val viewModel by viewModels<XYViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupStateListener()

        view.setOnKeyListener { _, _, keyEvent ->
            viewModel.onKeyEvent(keyEvent)
        }
    }

    private fun initializeViews(view: View) {
        listingBeforeImage = view.findViewById(R.id.xy_listing_before_image)

        listings = view.findViewById(R.id.xy_listings)
        listings.itemAnimator = null
        listings.adapter = ListingListAdapter()

        channelBeforeIcon = view.findViewById(R.id.xy_channel_before_icon)
        channelBeforeText = view.findViewById(R.id.xy_channel_before_text)

        channels = view.findViewById(R.id.xy_channels)
        channels.itemAnimator = null
        channels.adapter = ChannelListAdapter()

        channelIcon = view.findViewById(R.id.xy_channel_icon)
        listingImage = view.findViewById(R.id.xy_listing_image)
        listingTitle = view.findViewById(R.id.xy_listing_title)
        listingDescription = view.findViewById(R.id.xy_listing_description)
    }

    private fun setupStateListener() {
        repeatOnLifecycleStarted {
            viewModel.state.collectLatest(::renderState)
        }
    }

    private fun renderState(state: XYState) {
        Glide.with(requireContext())
            .load(state.beforeListingImage)
            .into(listingBeforeImage)

        Glide.with(requireContext())
            .load(state.beforeChannelIcon)
            .into(channelBeforeIcon)
        channelBeforeText.text = state.beforeChannelText

        Glide.with(requireContext())
            .load(state.cardIcon)
            .into(channelIcon)

        Glide.with(requireContext())
            .load(state.cardImage)
            .into(listingImage)

        listingTitle.text = state.cardTitle
        listingDescription.text = state.cardDescription

        submitListToHorizontalGrid(state.listingAfterList)
        submitListToVerticalGrid(state.channelAfterList)
    }

    private fun submitListToHorizontalGrid(list: List<Listing>) {
        listings.scrollToPosition(0)
        val adapter = listings.adapter as ListingListAdapter
        adapter.submitList(list)
    }


    private fun submitListToVerticalGrid(list: List<Channel>) {
        channels.scrollToPosition(0)
        val adapter = channels.adapter as ChannelListAdapter
        adapter.submitList(list)
    }
}