package com.keixxdd.weewee.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keixxdd.weewee.domain.usecases.RegisterUserUseCase
import com.keixxdd.weewee.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val registerUseCase: RegisterUserUseCase
): ViewModel() {

    sealed class GetResponseEvent{
        class Success<T>(val data: T): GetResponseEvent()
        class Error(val error: String?): GetResponseEvent()
        object Loading : GetResponseEvent()
        object NotLoading : GetResponseEvent()
    }

    private val _response = MutableStateFlow<GetResponseEvent>(GetResponseEvent.NotLoading)
    val response: StateFlow<GetResponseEvent> = _response

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
}