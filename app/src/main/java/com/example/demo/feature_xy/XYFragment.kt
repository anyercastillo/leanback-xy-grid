package com.example.demo.feature_xy

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.leanback.widget.HorizontalGridView
import androidx.leanback.widget.VerticalGridView
import com.example.demo.R
import com.example.demo.feature_xy.adapter.ChannelListAdapter
import com.example.demo.feature_xy.adapter.ListingListAdapter
import com.example.demo.feature_xy.utils.repeatOnLifecycleStarted
import com.example.demo.network.Channel
import kotlinx.coroutines.flow.collectLatest

class XYFragment : Fragment(R.layout.fragment_xy) {
    private lateinit var beforeX: AppCompatImageView
    private lateinit var beforeY: TextView
    private lateinit var cardX: TextView
    private lateinit var cardY: TextView
    private lateinit var afterY: VerticalGridView
    private lateinit var afterX: HorizontalGridView

    private lateinit var debugBeforeX: TextView
    private lateinit var debugX: TextView
    private lateinit var debugAfterX: TextView

    private lateinit var debugBeforeY: TextView
    private lateinit var debugY: TextView
    private lateinit var debugAfterY: TextView

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
        beforeX =
            view.findViewById<ConstraintLayout>(R.id.xy_before_x).findViewById(R.id.xy_item_x_image)
        afterX = view.findViewById(R.id.xy_after_x)
        afterX.itemAnimator = null
        afterX.adapter = ListingListAdapter()

        beforeY =
            view.findViewById<ConstraintLayout>(R.id.xy_before_y).findViewById(R.id.xy_item_y_text)
        afterY = view.findViewById(R.id.xy_after_y)
        afterY.itemAnimator = null
        afterY.adapter = ChannelListAdapter()

        val card = view.findViewById<ConstraintLayout>(R.id.xy_card)
        cardX = card.findViewById(R.id.xy_item_x_text)
        cardY = card.findViewById(R.id.xy_item_y_text)

        val card2 = view.findViewById<ConstraintLayout>(R.id.xy_card2)
        debugBeforeX = card2.findViewById(R.id.xy_item_x_before)
        debugX = card2.findViewById(R.id.xy_item_x_pivot)
        debugAfterX = card2.findViewById(R.id.xy_item_x_after)

        debugBeforeY = card2.findViewById(R.id.xy_item_y_before)
        debugY = card2.findViewById(R.id.xy_item_y_pivot)
        debugAfterY = card2.findViewById(R.id.xy_item_y_after)
    }

    private fun setupStateListener() {
        repeatOnLifecycleStarted {
            viewModel.state.collectLatest(::renderState)
        }
    }

    private fun renderState(state: XYState) {
//        beforeX.text = state.formattedBeforeX
        beforeY.text = state.formattedBeforeY
        cardX.text = state.formattedCardX
        cardY.text = state.formattedCardY

        debugBeforeX.text = state.formattedDebugXBefore
        debugX.text = state.formattedDebugX
        debugAfterX.text = state.formattedDebugXAfter

        debugBeforeY.text = state.formattedDebugYBefore
        debugY.text = state.formattedDebugY
        debugAfterY.text = state.formattedDebugYAfter

        submitListToHorizontalGrid(state.listingAfterList)
        submitListToVerticalGrid(state.channelAfterList)
    }

    private fun submitListToVerticalGrid(yList: List<Channel>) {
        val adapter = afterY.adapter as ChannelListAdapter
        afterY.scrollToPosition(0)
        adapter.submitList(yList)
    }

    private fun submitListToHorizontalGrid(xList: List<String>) {
        val adapter = afterX.adapter as ListingListAdapter
        afterX.scrollToPosition(0)
        adapter.submitList(xList)
    }
}