package com.keixxdd.weewee.domain.module

data class RegisterResponseBody(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String,
    val id: String
)
