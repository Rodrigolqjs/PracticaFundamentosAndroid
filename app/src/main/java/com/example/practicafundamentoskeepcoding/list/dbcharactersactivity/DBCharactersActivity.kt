package com.example.practicafundamentoskeepcoding.list.dbcharactersactivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicafundamentoskeepcoding.databinding.ActivityDbcharactersBinding
import com.example.practicafundamentoskeepcoding.list.listfragment.CharacterFightFragment
import com.example.practicafundamentoskeepcoding.list.listfragment.CharacterListFragment
import com.example.practicafundamentoskeepcoding.list.models.Character

interface DBCharactersCallBack {
    fun nextFragment(item: Character)
    fun goBack(winner: Character, looser: Character)
}

class DBCharactersActivity() : AppCompatActivity(), DBCharactersCallBack {

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
    override fun nextFragment(item: Character) {
        val fragment = CharacterFightFragment(item)
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commitNow()
    }

    override fun goBack(selected: Character, oponent: Character) {

        if (selected.health <= 0){
            selected.health = 0
        } else if (oponent.health <= 0) {
            oponent.health <= 0
        }

        val fragment = CharacterListFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commitNow()
    }


}