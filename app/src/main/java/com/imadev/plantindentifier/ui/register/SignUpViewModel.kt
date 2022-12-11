package com.imadev.plantindentifier.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imadev.plantindentifier.remote.models.RegisterModel
import com.imadev.plantindentifier.repository.PlantIdentificationRepo
import com.imadev.plantindentifier.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: PlantIdentificationRepo
) : ViewModel() {


    private val _registerLiveData = MutableLiveData<Resource<RegisterModel>>()
    val registerLiveData: LiveData<Resource<RegisterModel>> = _registerLiveData


    fun register(registerModel: RegisterModel) = viewModelScope.launch {
        repo.register(registerModel).collectLatest {
            _registerLiveData.postValue(it)
        }

    }

}