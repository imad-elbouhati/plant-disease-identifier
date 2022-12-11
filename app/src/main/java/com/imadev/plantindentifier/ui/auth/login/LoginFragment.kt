package com.imadev.plantindentifier.ui.auth.login

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.EncryptedSharedPreferences
import com.imadev.plantindentifier.MainActivity
import com.imadev.plantindentifier.R
import com.imadev.plantindentifier.data.FormValidation
import com.imadev.plantindentifier.data.remote.models.LoginModel
import com.imadev.plantindentifier.databinding.FragmentLoginBinding
import com.imadev.plantindentifier.ui.BaseFragmentBinding
import com.imadev.plantindentifier.ui.auth.AuthViewModel
import com.imadev.plantindentifier.utils.Constants.TOKEN_PREF
import com.imadev.plantindentifier.utils.Resource
import com.imadev.plantindentifier.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : BaseFragmentBinding<FragmentLoginBinding>() {

    private val viewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var preferences: SharedPreferences

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        observeSubscribers()

        binding.signUpTv.setOnClickListener {
            navigateToSignUp()
        }


        binding.signInBtn.setOnClickListener {
            loginValidation()
        }
    }

    private fun observeSubscribers() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
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
                    preferences.edit().putString(TOKEN_PREF,it.data.token).apply()
                    requireView().snackbar(getString(R.string.login_successful))
                    val directions = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(directions)
                }
            }
        }
    }


    private fun loginValidation() {
        binding.apply {

            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()

            if (FormValidation.areEditTextsEmpty(emailEt, passwordEt)) {
                requireView().snackbar(getString(R.string.empty_fields))
                return@apply
            }

            if (!FormValidation.isValidEmail(email)) {
                requireView().snackbar(getString(R.string.email_not_valid))
                return@apply
            }



            LoginModel(email, password).apply {
                viewModel.login(this)
            }
        }

    }


    private fun navigateToSignUp() {
        val directions = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(directions)
    }

}