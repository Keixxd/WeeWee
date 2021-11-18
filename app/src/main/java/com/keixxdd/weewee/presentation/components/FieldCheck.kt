package com.keixxdd.weewee.presentation.components

import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import com.keixxdd.weewee.R
import com.keixxdd.weewee.data.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object FieldCheck{

    private fun showErrorDrawables(field: EditText){
        field.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_error_24,0)
    }

    private fun hideErrorDrawables(field: EditText){
        field.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
    }

    fun checkEmail(email: EditText): Boolean{
        if(email.text.toString().isEmailValid()){
            hideErrorDrawables(email)
            return true
        }else {
            showErrorDrawables(email)
            return false
        }

    }

    fun checkField(field: EditText): Boolean{
        if (!field.text.isEmpty()){
            hideErrorDrawables(field)
            return true
        }else {
            showErrorDrawables(field)
            return false
        }
    }

    fun checkPasswords(pass: EditText, confirmPass: EditText): Boolean{
        if(pass.text.toString().equals(confirmPass.text.toString()) && (!pass.text.isEmpty() && !confirmPass.text.isEmpty())){
            hideErrorDrawables(pass)
            hideErrorDrawables(confirmPass)
            return true
        }else{
            showErrorDrawables(pass)
            showErrorDrawables(confirmPass)
            return false
        }
    }

    fun String.isEmailValid(): Boolean =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}