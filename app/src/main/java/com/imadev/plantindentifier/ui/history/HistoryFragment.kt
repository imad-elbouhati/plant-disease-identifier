package com.imadev.plantindentifier.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imadev.plantindentifier.databinding.FragmentHistoryBinding
import com.imadev.plantindentifier.ui.BaseFragmentBinding


class HistoryFragment : BaseFragmentBinding<FragmentHistoryBinding>() {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)

}