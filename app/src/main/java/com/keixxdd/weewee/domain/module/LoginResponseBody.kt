package com.keixxdd.weewee.domain.module

data class LoginResponseBody(
    val email: String,
    val password: String,
    val id: String
)
