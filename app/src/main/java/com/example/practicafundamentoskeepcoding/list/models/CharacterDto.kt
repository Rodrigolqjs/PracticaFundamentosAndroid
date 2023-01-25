package com.example.practicafundamentoskeepcoding.list.models

data class CharacterDto(
    val id: String,
    val photo: String,
    var favorite: Boolean,
    val name: String,
    val description: String,
)