package com.imadev.plantindentifier.ui.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.imadev.plantindentifier.MainActivity
import com.imadev.plantindentifier.R
import com.imadev.plantindentifier.data.FormValidation
import com.imadev.plantindentifier.data.remote.models.RegisterModel
import com.imadev.plantindentifier.databinding.FragmentSignUpBinding
import com.imadev.plantindentifier.ui.BaseFragmentBinding
import com.imadev.plantindentifier.ui.auth.AuthViewModel
import com.imadev.plantindentifier.utils.Resource
import com.imadev.plantindentifier.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SignUpFragment"

@AndroidEntryPoint
class SignUpFragment : BaseFragmentBinding<FragmentSignUpBinding>() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeSubscribers()


        binding.signUpBtn.setOnClickListener {
            registerValidation()
        }


        binding.signInTv.setOnClickListener {
            navigateToLogin()
        }
    }


    private fun observeSubscribers() {
        viewModel.registerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    requireView().snackbar(getString(R.string.something_went_wrong))
                }
                Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading...")
                    (requireActivity() as MainActivity).showProgressBar()
                }
                is Resource.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    requireView().snackbar(getString(R.string.account_created_successfully))

                    navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin() {
        val directions = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        findNavController().navigate(directions)
    }


    private fun registerValidation() {
        binding.apply {

            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()
            val rePassword = reEnterPasswordEt.text.toString()

            if (FormValidation.areEditTextsEmpty(emailEt, passwordEt, reEnterPasswordEt)) {
                requireView().snackbar(getString(R.string.empty_fields))
                return@apply
            }

            if (!FormValidation.isValidEmail(email)) {
                requireView().snackbar(getString(R.string.email_not_valid))
                return@apply
            }


            if (!FormValidation.arePasswordsIdentical(password, rePassword)) {
                requireView().snackbar(getString(R.string.passwords_are_not_identical))
                return
            }

            RegisterModel(email, email, password).apply {
                viewModel.register(this)
            }
        }

    }

}