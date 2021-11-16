package com.keixxdd.weewee.data.repository

import android.content.res.Resources
import com.keixxdd.weewee.domain.module.LoginData
import com.keixxdd.weewee.domain.module.RegisterData
import com.keixxdd.weewee.utils.Resource

interface MainRepository {

    suspend fun registerUser(
        email: String,
        firstname: String,
        lastname: String,
        password: String
    ): Resource<RegisterData?>

    suspend fun checkLogin(
        email: String,
        password: String
    ): Resource<LoginData?>

    /*suspend fun uploadAvatar(): Resource<AvatarData>

    suspend fun updateProfile(): Resource<ProfileData>*/

}