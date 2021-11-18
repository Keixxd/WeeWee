package com.keixxdd.weewee.data.repository

import com.keixxdd.weewee.data.remote.NetworkService
import com.keixxdd.weewee.domain.module.LoginData
import com.keixxdd.weewee.domain.module.LoginResponseBody
import com.keixxdd.weewee.domain.module.RegisterData
import com.keixxdd.weewee.domain.module.RegisterResponseBody
import com.keixxdd.weewee.utils.Resource
import javax.inject.Inject
import kotlin.math.log

class DefaultRepository @Inject constructor(
    private val service: NetworkService
): MainRepository {
    override suspend fun registerUser(
        email: String,
        firstname: String,
        lastname: String,
        password: String
    ): Resource<RegisterResponseBody?> {
        return try{
            val data = RegisterData(
                email = email,
                firstname = firstname,
                lastname = lastname,
                password = password
            )
            val response = service.registerUser(
                registerData = data
            )
            val result = response.body()
            when(response.isSuccessful){
                true -> return Resource.Success(result)
                else -> return Resource.Failure(null)
            }
        }catch (e: Exception){
            Resource.Failure(e.cause)
        }
    }

    override suspend fun checkLogin(
        email: String, password: String
    ): Resource<LoginResponseBody?> {
        return try{
            val data = LoginData(
                email = email,
                password = password
            )
            val response = service.checkLogin(
                loginData = data
            )
            val result = response.body()
            when(response.isSuccessful){
                true -> return Resource.Success(result)
                else -> return Resource.Failure(null)
            }
        }catch (e: Exception){
            Resource.Failure(e.cause)
        }
    }
}