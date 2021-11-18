package com.keixxdd.weewee.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.keixxdd.weewee.R
import com.keixxdd.weewee.databinding.FragmentLoginBinding
import com.keixxdd.weewee.domain.module.LoginResponseBody
import com.keixxdd.weewee.presentation.components.FieldCheck
import com.keixxdd.weewee.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewmodel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.authorization_login_fragment_title)

        bindLoginButton()
    }

    private fun bindLoginButton() {
        binding.loginButton.setOnClickListener{
            if(validateLogin()){
                proceedLogin()
            }else{
                Snackbar.make(binding.root, "Проверьте данные в выделенных полях", 5000)
                    .setAnchorView(binding.loginButton)
                    .show()
            }
        }
    }

    private fun validateLogin(): Boolean {
        return getValidate()
    }

    private fun getValidate(): Boolean {
        with(binding){
            val emailResult = FieldCheck.checkEmail(etLoginEmail)
            val passwordResult = FieldCheck.checkField(etLoginPassword)
            return emailResult && passwordResult
        }
    }

    private fun proceedLogin() {
        with(binding){
            viewmodel.checkLogin(
                email = etLoginEmail.text.toString(),
                password = etLoginPassword.text.toString()
            )
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.response.collectLatest { data ->
                when (data) {
                    is MainViewModel.GetResponseEvent.Success<*> -> {
                        Log.d("info_log", (data.data as LoginResponseBody).id)
                        if(findNavController().currentDestination?.id == R.id.loginFragment)
                            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                    }
                    is MainViewModel.GetResponseEvent.Error -> {
                        Snackbar.make(binding.root, data.error.toString(), 5000)
                            .setAnchorView(binding.loginButton)
                            .show()
                        binding.loginButton.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                    }
                    is MainViewModel.GetResponseEvent.Loading -> {
                        binding.loginButton.isEnabled = false
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


}