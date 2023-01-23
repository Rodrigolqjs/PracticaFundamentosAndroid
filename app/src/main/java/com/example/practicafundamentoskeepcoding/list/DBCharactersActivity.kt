package com.example.practicafundamentoskeepcoding.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicafundamentoskeepcoding.databinding.ActivityDbcharactersBinding

class DBCharactersActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityDbcharactersBinding
    private val viewModel: DBCharactersActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDbcharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val token = intent.getStringExtra("TOKEN").toString()

        viewModel.getCharacters(token)
        viewModel.dbCharactersLiveDataList.observe(this) {
            when(it) {
                is DBCharactersActivityViewModel.DBCharactersActivityState.Success -> {
                    if (savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .replace(binding.container.id, CharacterListFragment())
                            .commitNow()
                    }
                }
                is DBCharactersActivityViewModel.DBCharactersActivityState.Error -> {
                }
                is DBCharactersActivityViewModel.DBCharactersActivityState.Loading -> {

                }
            }
        }


    }

}