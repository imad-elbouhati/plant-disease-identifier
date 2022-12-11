package com.imadev.plantindentifier.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imadev.plantindentifier.data.remote.models.LoginResponse
import com.imadev.plantindentifier.data.remote.models.LoginModel
import com.imadev.plantindentifier.data.remote.models.RegisterModel
import com.imadev.plantindentifier.repository.PlantIdentificationRepo
import com.imadev.plantindentifier.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: PlantIdentificationRepo
) : ViewModel() {


    private val _registerLiveData = MutableLiveData<Resource<RegisterModel>>()
    val registerLiveData: LiveData<Resource<RegisterModel>> = _registerLiveData


    private val _loginLiveData = MutableLiveData<Resource<LoginResponse>>()
    val loginLiveData: LiveData<Resource<LoginResponse>> = _loginLiveData


    fun register(registerModel: RegisterModel) = viewModelScope.launch {
        repo.register(registerModel).collectLatest {
            _registerLiveData.postValue(it)
        }
    }


    fun login(loginModel: LoginModel) = viewModelScope.launch {
        repo.login(loginModel).collectLatest {
            _loginLiveData.postValue(it)
        }
    }

}