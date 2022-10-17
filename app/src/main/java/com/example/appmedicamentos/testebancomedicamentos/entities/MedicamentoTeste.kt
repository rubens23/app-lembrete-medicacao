package com.example.appmedicamentos.testebancomedicamentos.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class MedicamentoTeste(
    @PrimaryKey(autoGenerate = false)
    val nomeMedicamento: String,
    val horaPrimeiraDose: String,
    val qntDoses: Int,
    val totalDiasTratamento: Int,
    val tratamentoFinalizado: Boolean
): Serializable
