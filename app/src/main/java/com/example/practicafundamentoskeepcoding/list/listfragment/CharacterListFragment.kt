package com.example.practicafundamentoskeepcoding.list.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicafundamentoskeepcoding.databinding.FragmentCharacterListBinding
import com.example.practicafundamentoskeepcoding.list.models.Character
import com.example.practicafundamentoskeepcoding.list.dbcharactersactivity.DBCharactersActivityViewModel
import com.example.practicafundamentoskeepcoding.list.dbcharactersactivity.DBCharactersCallBack


class CharacterListFragment : Fragment(), MainAdapterCallBack, ShowToastCustom {
    private val viewModel: DBCharactersActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentCharacterListBinding
    private val adapter = MainAdapter(this)
    private var list: MutableList<Character> = mutableListOf()

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
            characterList.adapter = adapter
        }
    }

    override fun onItemClick(item: Character) {
        //TODO: Saltar a otro fragment
        var callback = activity as? DBCharactersCallBack
        callback?.nextFragment(item)
    }

    override fun showToastFinal(message: String) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show()
    }

}
