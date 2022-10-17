package com.example.appmedicamentos.philliplacknerrelationtutorial.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.Director
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.School

data class SchoolAndDirector(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName"
    )
    val director: Director
)
