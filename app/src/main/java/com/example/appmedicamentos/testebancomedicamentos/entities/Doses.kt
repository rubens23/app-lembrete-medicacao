package com.example.appmedicamentos.testebancomedicamentos.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Doses(
    @PrimaryKey(autoGenerate = true)
    val idDose: Int = 0,
    val nomeMedicamento: String,
    val horarioDose: String,
    val jaTomouDose: Boolean
): Serializable
