package com.keixxdd.weewee.presentation.fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.keixxdd.weewee.R
import com.keixxdd.weewee.databinding.FragmentRegisterBinding
import com.keixxdd.weewee.domain.module.RegisterData
import com.keixxdd.weewee.domain.module.RegisterResponseBody
import com.keixxdd.weewee.presentation.components.FieldCheck
import com.keixxdd.weewee.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.time.Duration

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewmodel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.registration_register_fragment_title)

        onBindCheckBox()
        onBindRegisterButton()
    }

    private fun onBindCheckBox(){
        binding.showPassCheckBox.setOnClickListener {
            when((it as CheckBox).isChecked){
                true -> {
                    binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    binding.etConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }
                false -> {
                    // 129 - making password visible
                    // it is actually (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    binding.etPassword.inputType = 129
                    binding.etConfirmPassword.inputType = 129
                }
            }
        }
    }

    fun onBindRegisterButton(){
        binding.registerButton.setOnClickListener{
            if(validateRegister()){
                proceedRegister()
            }else{
                Snackbar.make(binding.root, "Проверьте данные в выделенных полях", 5000)
                    .setAnchorView(binding.registerButton)
                    .show()
            }
        }
    }

    private fun proceedRegister() {

        with(binding){
            viewmodel.registerUser(
                email = etEmail.text.toString(),
                firstname = etFirstname.text.toString(),
                lastname = etLastname.text.toString(),
                password = etPassword.text.toString()
            )
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.response.collectLatest { data ->
                when (data) {
                    is MainViewModel.GetResponseEvent.Success<*> -> {
                        Log.d("info_log", (data.data as RegisterResponseBody).id)
                        if(findNavController().currentDestination?.id == R.id.registerFragment)
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    is MainViewModel.GetResponseEvent.Error -> {
                        Snackbar.make(binding.root, data.error.toString(), 5000)
                            .setAnchorView(binding.registerButton)
                            .show()
                        binding.registerButton.isEnabled = true
                        binding.registerProgressBar.visibility = View.GONE
                    }
                    is MainViewModel.GetResponseEvent.Loading -> {
                        binding.registerButton.isEnabled = false
                        binding.registerProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun validateRegister(): Boolean {
        return getValidate()
    }

    private fun getValidate(): Boolean {
        with(binding) {
            val lastNameResult = FieldCheck.checkField(etLastname)
            val firstNameResult = FieldCheck.checkField(etFirstname)
            val patronymicResult = FieldCheck.checkField(etPatronymic)
            val emailResult = FieldCheck.checkEmail(etEmail)
            val passwordResult = FieldCheck.checkPasswords(etPassword, etConfirmPassword)
            return lastNameResult
                    && firstNameResult
                    && patronymicResult
                    && emailResult
                    && passwordResult
        }
    }
}

