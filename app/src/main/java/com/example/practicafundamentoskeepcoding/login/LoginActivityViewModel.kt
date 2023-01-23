package com.example.practicafundamentoskeepcoding.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import okhttp3.RequestBody.Companion.toRequestBody


class LoginActivityViewModel: ViewModel() {

    val loginLiveDataList: MutableLiveData<LoginActivityState> by lazy {
        MutableLiveData<LoginActivityState>()
    }

    fun login(email: String, password: String) {
        setValueOnMainThread(LoginActivityState.Loading)
        val client = OkHttpClient()
        val url = "https://dragonball.keepcoding.education/api/auth/login" // /api/heros/all
        val credential = Credentials.basic(email, password)
        val request = Request.Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", credential)
            .method("POST", "".toRequestBody())
            .url(url)
            .build()
        val call = client.newCall(request)
        call.enqueue(
            object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    setValueOnMainThread(LoginActivityState.Error(e.message.toString()))
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyResponse = response.body?.string()
                    setValueOnMainThread(LoginActivityState.Success(bodyResponse.toString()))
                }
            }
        )
    }

    fun setValueOnMainThread(value: LoginActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            loginLiveDataList.value = value
        }
    }

    sealed class LoginActivityState {
        data class Success(val token: String): LoginActivityState()
        data class Error(val message: String): LoginActivityState()
        object Loading: LoginActivityState()
    }
}