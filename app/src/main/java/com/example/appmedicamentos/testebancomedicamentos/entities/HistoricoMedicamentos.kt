package com.example.appmedicamentos.testebancomedicamentos.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["nomeMedicamento", "dataFinalizacao"])
data class HistoricoMedicamentos(
    val nomeMedicamento: String,
    val periodoTotalDeTratamentoEmDias: Int,
    val dataFinalizacao: String
)
