package com.example.practicafundamentoskeepcoding.list

data class Character(
    val id: String,
    val photo: String,
    var favorite: Boolean,
    val name: String,
    val description: String,
    val maxHealth: Int,
    val health: Int
)