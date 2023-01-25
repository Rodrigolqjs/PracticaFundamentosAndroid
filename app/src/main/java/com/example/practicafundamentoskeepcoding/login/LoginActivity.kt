package com.example.practicafundamentoskeepcoding.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.practicafundamentoskeepcoding.databinding.ActivityLoginBinding
import com.example.practicafundamentoskeepcoding.list.dbcharactersactivity.DBCharactersActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loginLiveDataList.observe(this) {
            when(it) {
                is LoginActivityViewModel.LoginActivityState.Success -> {
                    val token = it.token
                    val nextPage = Intent(this, DBCharactersActivity::class.java).also {
                        it.putExtra("TOKEN", token)
                        startActivity(it)
                    }
                    finish()
                }
                is LoginActivityViewModel.LoginActivityState.Error -> {
                }
                is LoginActivityViewModel.LoginActivityState.Loading -> {

                }
            }
        }
        setListeners()

    }



    fun setListeners() {
        with(binding) {
            loginButton.setOnClickListener {
                viewModel.login(etName.text.toString(), etPassword.text.toString())
            }
        }
    }
}