package com.keixxdd.weewee.data.remote

import com.keixxdd.weewee.domain.module.LoginData
import com.keixxdd.weewee.domain.module.RegisterData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkService {

    @POST("registerUser")
    suspend fun registerUser(
        @Body registerData: RegisterData
    ): Response<RegisterData>

    @POST("checkLogin")
    suspend fun checkLogin(
        @Body loginData: LoginData
    ): Response<LoginData>
}