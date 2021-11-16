package com.keixxdd.weewee.presentation.fragments

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.keixxdd.weewee.R
import com.keixxdd.weewee.databinding.FragmentRegisterBinding
import com.keixxdd.weewee.presentation.viewmodels.MainViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewmodel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBindCheckBox()
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
        binding.button.setOnClickListener{
            validateRegister()
        }
    }

    private fun validateRegister() {
        TODO("Not yet implemented")
    }
}