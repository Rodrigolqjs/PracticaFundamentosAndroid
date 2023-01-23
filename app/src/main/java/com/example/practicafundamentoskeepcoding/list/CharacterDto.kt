package com.example.practicafundamentoskeepcoding.list

data class CharacterDto(
    val id: String,
    val photo: String,
    var favorite: Boolean,
    val name: String,
    val description: String,
)