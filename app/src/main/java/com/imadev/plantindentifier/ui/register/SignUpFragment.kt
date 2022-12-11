package com.imadev.plantindentifier.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.imadev.plantindentifier.databinding.FragmentSignUpBinding
import com.imadev.plantindentifier.remote.models.RegisterModel
import com.imadev.plantindentifier.ui.BaseFragmentBinding
import com.imadev.plantindentifier.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SignUpFragment"

@AndroidEntryPoint
class SignUpFragment : BaseFragmentBinding<FragmentSignUpBinding>() {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.register(RegisterModel("imad","imadimad@gmail.com","hello"))

        viewModel.registerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Log.d(TAG, "onViewCreated: ${it.throwable?.message}")
                }
                Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading...")
                }
                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated: ${it.data}")
                }
            }

        }

    }

}