package com.example.practicafundamentoskeepcoding.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicafundamentoskeepcoding.databinding.FragmentCharacterListBinding

class CharacterListFragment : Fragment(), MainAdapterCallBack {
    private val viewModel: DBCharactersActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentCharacterListBinding
    private val adapter = MainAdapter(this)
    private var list: List<Character> = emptyList<Character>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCharacterListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = viewModel.characters
        createRecycler()
    }

    private fun createRecycler() {
        adapter.updateList(list)
        with(binding) {
            characterList.layoutManager = LinearLayoutManager(context)
            characterList.setHasFixedSize(true)
            characterList.adapter = adapter
        }
    }

    override fun onItemClick(item: Character) {
        //TODO: Saltar a otro fragment


    }


}
