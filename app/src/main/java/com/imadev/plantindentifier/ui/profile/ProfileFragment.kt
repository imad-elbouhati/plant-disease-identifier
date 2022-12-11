package com.imadev.plantindentifier.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.imadev.plantindentifier.databinding.FragmentProfileBinding
import com.imadev.plantindentifier.ui.BaseFragmentBinding

class ProfileFragment : BaseFragmentBinding<FragmentProfileBinding>() {


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

}