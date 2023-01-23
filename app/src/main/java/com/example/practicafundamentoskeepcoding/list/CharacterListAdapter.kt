package com.example.practicafundamentoskeepcoding.list

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafundamentoskeepcoding.databinding.CharacterItemBinding
import com.squareup.picasso.Picasso

interface MainAdapterCallBack {
    fun onItemClick(item: Character)
}

class MainAdapter(var activity: MainAdapterCallBack): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var items = listOf<Character>()

    inner class MainViewHolder(val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character, position: Int) {
            with(binding) {
                if (item.health <= 0) {
                    root.setBackgroundColor(Color.GRAY)
                }
                if(item.name !== "") {
                    Picasso.get().load(item.photo).into(characterImage)
                }
                characterName.text = item.name
                characterHealth.text = "${item.health.toString()}/${item.maxHealth.toString()}"
            }
            activity.onItemClick(item)
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
        holder.bind(items[position], position)
    }

    fun updateList(list: List<Character>) {
        items = list
        notifyDataSetChanged()
    }

}