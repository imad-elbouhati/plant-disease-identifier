package com.imadev.plantindentifier.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imadev.plantindentifier.data.remote.models.PredictionResponse
import com.imadev.plantindentifier.repository.PlantIdentificationRepo
import com.imadev.plantindentifier.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: PlantIdentificationRepo
) : ViewModel() {

    private val _predictionsLiveData = MutableLiveData<Resource<PredictionResponse>>()
    val predictionsLiveData: LiveData<Resource<PredictionResponse>> = _predictionsLiveData

    fun predict(imageUrl: String) = viewModelScope.launch {
        repo.predict(imageUrl).collectLatest {
            _predictionsLiveData.postValue(it)
        }
    }
}