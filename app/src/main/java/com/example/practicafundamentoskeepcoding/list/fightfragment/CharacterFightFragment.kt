package com.example.practicafundamentoskeepcoding.list.listfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicafundamentoskeepcoding.databinding.CharacterFightItemBinding
import com.example.practicafundamentoskeepcoding.databinding.FragmentCharacterFightBinding
import com.example.practicafundamentoskeepcoding.databinding.FragmentCharacterListBinding
import com.example.practicafundamentoskeepcoding.list.models.Character
import com.example.practicafundamentoskeepcoding.list.dbcharactersactivity.DBCharactersActivityViewModel
import com.example.practicafundamentoskeepcoding.list.dbcharactersactivity.DBCharactersCallBack
import com.squareup.picasso.Picasso
import kotlin.random.Random

class CharacterFightFragment(private var selecterCharacter: Character) : Fragment(){
    private val viewModel: DBCharactersActivityViewModel by activityViewModels()
    private lateinit var binding: CharacterFightItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = CharacterFightItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var randomCharacter: Character
        var randomOponent: Character

        do {
            randomCharacter = viewModel.characters[Random.nextInt(viewModel.characters.size)]
            randomOponent = randomCharacter
        } while (selecterCharacter.id == randomCharacter.id || randomCharacter.health <= 0)

        with(binding) {
            Picasso.get().load(selecterCharacter.photo).into(mainCharacterImage)
            Picasso.get().load(randomOponent.photo).into(oponentImage)

            mainCharacterName.text = selecterCharacter.name
            oponentName.text = randomOponent.name
            mainCharacterProgressBar.setProgress(selecterCharacter.health, false)
            oponentProgressBar.setProgress(randomOponent.health, false)


                fightButton.setOnClickListener {
                    val callback = activity as? DBCharactersCallBack

                    val damage1 = selecterCharacter.health - Random.nextInt(10,60)
                    selecterCharacter.health = damage1
                    val damage2 = randomOponent.health - Random.nextInt(10,60)
                    randomOponent.health = damage2
                    if(selecterCharacter.health <= 0 || randomCharacter.health <= 0) {
                        callback?.goBack(selecterCharacter, randomOponent)
                    }
                    mainCharacterProgressBar.setProgress(selecterCharacter.health, false)
                    oponentProgressBar.setProgress(randomOponent.health, false)
                }
        }
    }


}
