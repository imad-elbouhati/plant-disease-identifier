package com.imadev.plantindentifier.repository

import com.imadev.plantindentifier.data.remote.PlantIdentificationService
import com.imadev.plantindentifier.data.remote.models.LoginModel
import com.imadev.plantindentifier.data.remote.models.RegisterModel
import com.imadev.plantindentifier.utils.safeApiCall
import javax.inject.Inject

class PlantIdentificationRepo @Inject constructor(
    private val plantIdentificationService: PlantIdentificationService
) {
    fun register(registerModel: RegisterModel) = safeApiCall {
        plantIdentificationService.register(registerModel)
    }

    fun login(loginModel: LoginModel) = safeApiCall {
        plantIdentificationService.login(loginModel)
    }
}