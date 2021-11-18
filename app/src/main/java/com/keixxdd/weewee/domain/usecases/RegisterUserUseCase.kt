package com.keixxdd.weewee.domain.usecases

import com.keixxdd.weewee.data.repository.DefaultRepository
import com.keixxdd.weewee.domain.module.RegisterData
import com.keixxdd.weewee.domain.module.RegisterResponseBody
import com.keixxdd.weewee.utils.Resource
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: DefaultRepository
) {

    suspend operator fun invoke(
        email: String,
        firstname: String,
        lastname: String,
        password: String
    ): Resource<RegisterResponseBody?>{
        return repository.registerUser(
            email = email,
            firstname = firstname,
            lastname = lastname,
            password = password
        )
    }

}