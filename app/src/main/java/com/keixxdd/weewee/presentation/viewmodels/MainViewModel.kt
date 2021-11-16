package com.keixxdd.weewee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keixxdd.weewee.domain.usecases.CheckLoginUseCase
import com.keixxdd.weewee.domain.usecases.RegisterUserUseCase
import com.keixxdd.weewee.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val registerUseCase: RegisterUserUseCase,
    private val checkLoginUseCase: CheckLoginUseCase
): ViewModel() {

    sealed class GetResponseEvent{
        class Success<T>(val data: T): GetResponseEvent()
        class Error(val error: String?): GetResponseEvent()
        object Loading : GetResponseEvent()
        object NotLoading : GetResponseEvent()
    }

    private val _response = MutableStateFlow<GetResponseEvent>(GetResponseEvent.NotLoading)
    val response = _response.asStateFlow()

    fun registerUser(
        email: String,
        firstname: String,
        lastname: String,
        password: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _response.value = GetResponseEvent.Loading
            when(val registerCallback = registerUseCase.invoke(
                email = email,
                firstname = firstname,
                lastname = lastname,
                password = password
            )){
                is Resource.Success -> {
                    if(registerCallback.data == null)
                        _response.value = GetResponseEvent.Error("Unexpected Error")
                    else
                        _response.value = GetResponseEvent.Success(registerCallback.data)
                }
                is Resource.Failure -> {
                    _response.value = GetResponseEvent.Error(registerCallback.message?.message)
                }
            }
        }
    }

    fun checkLogin(
        email: String,
        password: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _response.value = GetResponseEvent.Loading
            when(val loginCallback = checkLoginUseCase.invoke(
                email = email,
                password = password
            )){
                is Resource.Success -> {
                    if(loginCallback.data == null)
                        _response.value = GetResponseEvent.Error("Unexpected Error")
                    else
                        _response.value = GetResponseEvent.Success(loginCallback.data)
                }
                is Resource.Failure -> {
                    _response.value = GetResponseEvent.Error(loginCallback.message?.message)
                }
            }
        }
    }
}