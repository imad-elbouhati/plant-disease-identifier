package com.imadev.plantindentifier.remote

import com.imadev.plantindentifier.remote.models.RegisterModel
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PlantIdentificationService {

    @POST("register")
    suspend fun register(@Body registerModel: RegisterModel) : RegisterModel
}