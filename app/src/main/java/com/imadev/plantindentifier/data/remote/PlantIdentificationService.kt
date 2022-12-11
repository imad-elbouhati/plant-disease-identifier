package com.imadev.plantindentifier.data.remote

import com.imadev.plantindentifier.data.remote.models.LoginModel
import com.imadev.plantindentifier.data.remote.models.LoginResponse
import com.imadev.plantindentifier.data.remote.models.RegisterModel
import retrofit2.http.Body
import retrofit2.http.POST

interface PlantIdentificationService {

    @POST("register")
    suspend fun register(@Body registerModel: RegisterModel) : RegisterModel

    @POST("login")
    suspend fun login(@Body registerModel: LoginModel) : LoginResponse
}