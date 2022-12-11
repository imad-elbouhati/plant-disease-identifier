package com.imadev.plantindentifier.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragmentBinding<V : ViewBinding> : Fragment() {

    private var _binding: V? = null
    protected val binding get() = _binding!!

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): V


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = createViewBinding(inflater, container)

        return binding.root
    }

}