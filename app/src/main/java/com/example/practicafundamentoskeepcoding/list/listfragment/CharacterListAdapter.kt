package com.example.practicafundamentoskeepcoding.list.listfragment

import android.graphics.Color
import android.support.annotation.ColorRes
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafundamentoskeepcoding.R
import com.example.practicafundamentoskeepcoding.databinding.CharacterItemBinding
import com.example.practicafundamentoskeepcoding.list.dbcharactersactivity.DBCharactersCallBack
import com.example.practicafundamentoskeepcoding.list.models.Character
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

interface MainAdapterCallBack {
    fun onItemClick(item: Character)
}

interface ShowToastCustom {
    fun showToastFinal(message: String)

}

class MainAdapter(var activity: MainAdapterCallBack): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var items: MutableList<Character> = mutableListOf()

    inner class MainViewHolder(val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character) {
            with(binding) {
                if (item.health > 0) {
                    root.setBackgroundColor(Color.parseColor("#F39000"))
                    characterHealth.text = "${item.health.toString()}/${item.maxHealth.toString()}"
                } else if(item.health <= 0) {
                    root.setBackgroundColor(Color.GRAY)
                    characterHealth.text = "0/100"
                }
                Picasso.get().load(item.photo).into(characterImage)
                characterName.text = item.name
            }
            var lastCharacterList: List<Character> = items.filter {
                it.health > 0
            }
            if(item.health > 0 && lastCharacterList.size !== 1) {
                binding.root.setOnClickListener {
                    activity.onItemClick(item)
                }
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder (
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateList(list: MutableList<Character>) {
        var callbackToast = activity as? ShowToastCustom

        var availableCharacters: List<Character> = list.filter {
            it.health > 0
        }
        when(availableCharacters.size) {
            0 -> callbackToast?.showToastFinal("The game is over")
            1 -> callbackToast?.showToastFinal("The winner is ${availableCharacters[0].name}")
        }

        items.addAll(list)
    }

}