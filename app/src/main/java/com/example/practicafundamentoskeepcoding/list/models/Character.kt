package com.example.practicafundamentoskeepcoding.list.models

data class Character(
    val id: String,
    val photo: String,
    val name: String,
    val maxHealth: Int,
    var health: Int
)