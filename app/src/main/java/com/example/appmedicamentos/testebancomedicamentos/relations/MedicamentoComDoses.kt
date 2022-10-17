package com.example.appmedicamentos.testebancomedicamentos.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appmedicamentos.testebancomedicamentos.entities.Doses
import com.example.appmedicamentos.testebancomedicamentos.entities.MedicamentoTeste
import java.io.Serializable

data class MedicamentoComDoses(
    @Embedded val medicamentoTeste: MedicamentoTeste,
    @Relation(
        parentColumn = "nomeMedicamento",
        entityColumn = "nomeMedicamento"
    )
    val listaDoses: List<Doses>
): Serializable
