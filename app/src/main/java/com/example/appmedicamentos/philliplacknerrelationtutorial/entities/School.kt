package com.example.appmedicamentos.philliplacknerrelationtutorial.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class School(
    @PrimaryKey(autoGenerate = false)
    val schoolName: String

)

/*
Medicamento
nome(primary key)
qntdoses
tratamentofinalizado
 */
