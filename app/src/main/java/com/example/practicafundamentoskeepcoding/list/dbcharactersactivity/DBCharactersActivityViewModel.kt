package com.example.practicafundamentoskeepcoding.list.dbcharactersactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicafundamentoskeepcoding.list.models.Character
import com.example.practicafundamentoskeepcoding.list.models.CharacterDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class DBCharactersActivityViewModel: ViewModel() {

    var characters: MutableList<Character> = mutableListOf()

    val dbCharactersLiveDataList: MutableLiveData<DBCharactersActivityState> by lazy {
        MutableLiveData<DBCharactersActivityState>()
    }

    fun getCharacters(token: String) {
        setValueOnMainThread(DBCharactersActivityState.Loading)
        val client = OkHttpClient()
        val url = "https://dragonball.keepcoding.education/api/heros/all"
        val formBody = FormBody.Builder()
            .add("name", "")
            .build()
        val request = Request.Builder()
            .post(formBody)
            .addHeader("Authorization", "Bearer $token")
            .url(url)
            .build()
        val call = client.newCall(request)
        call.enqueue(
            object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    setValueOnMainThread(DBCharactersActivityState.Error(e.message.toString()))
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val bodyResponse = response.body?.string()

                        val arrayOfCharaters: Array<CharacterDto> = Gson().fromJson(bodyResponse, Array<CharacterDto>::class.java)
                        val listofCharacters = arrayOfCharaters.map {
                            Character(
                                id = it.id,
                                photo = it.photo,
                                name = it.name,
                                maxHealth = 100,
                                health = 100
                            )
                        }
                        characters.addAll(listofCharacters)
                        setValueOnMainThread(DBCharactersActivityState.Success(listofCharacters))
                    } catch (ex: Exception) {

                    }


                }
            }
        )
    }

    fun setValueOnMainThread(value: DBCharactersActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            dbCharactersLiveDataList.value = value
        }
    }

    sealed class DBCharactersActivityState {
        data class Success(val token: List<Character>): DBCharactersActivityState()
        data class Error(val message: String): DBCharactersActivityState()
        object Loading: DBCharactersActivityState()
    }

}