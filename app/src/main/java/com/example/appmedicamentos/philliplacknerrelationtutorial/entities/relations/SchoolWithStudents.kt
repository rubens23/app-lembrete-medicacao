package com.example.appmedicamentos.philliplacknerrelationtutorial.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.School
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.Student

data class SchoolWithStudents(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName"
    )
    val students: List<Student>
)
/*
medicamento com doses
embedded Medicamento
parentColumn (nomeMedicamento)
entity column (nomeMedicamento)
val doses: List<Doses>

 */