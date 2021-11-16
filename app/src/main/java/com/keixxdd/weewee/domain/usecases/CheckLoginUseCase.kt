package com.keixxdd.weewee.domain.usecases

import com.keixxdd.weewee.data.repository.DefaultRepository
import com.keixxdd.weewee.domain.module.LoginData
import com.keixxdd.weewee.utils.Resource
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val repository: DefaultRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Resource<LoginData?>{

        return repository.checkLogin(
            email = email,
            password = password
        )
    }
}