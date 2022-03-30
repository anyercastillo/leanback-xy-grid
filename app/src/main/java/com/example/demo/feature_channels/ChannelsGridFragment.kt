package com.example.demo.feature_channels

import android.os.Bundle
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import com.example.demo.feature_channels.adapter.ChannelsAdapter

class ChannelsGridFragment : RowsSupportFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChannelsAdapter()
    }
}