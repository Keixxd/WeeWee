package com.keixxdd.weewee.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.keixxdd.weewee.R
import com.keixxdd.weewee.databinding.ActivityMainBinding
import com.keixxdd.weewee.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.button.setOnClickListener{
            viewModel.registerUser(
                "aaaa@mail.ru",
                "AAAA",
                "BBBB",
                "12345"
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.response.collectLatest{ data ->
                when (data) {
                    is MainViewModel.GetResponseEvent.Success<*> -> {
                        binding.textView.text = "Success!!"
                    }
                    is MainViewModel.GetResponseEvent.Error -> {
                        binding.textView.text = data.error
                    }
                    is MainViewModel.GetResponseEvent.Loading -> {
                        binding.textView.text = "loading..."
                    }
                }
            }
        }*/
    }
}