package com.example.appmedicamentos.testebancomedicamentos.entities

import androidx.room.Entity

@Entity(primaryKeys = ["nomeMedicamento", "dataFinalizacao"])
data class HistoricoMedicamentos(
    val nomeMedicamento: String,
    val periodoTotalDeTratamentoEmDias: Int,
    val dataFinalizacao: String
)
