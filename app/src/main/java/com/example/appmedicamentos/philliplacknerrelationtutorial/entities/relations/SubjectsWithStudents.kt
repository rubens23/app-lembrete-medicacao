package com.example.appmedicamentos.philliplacknerrelationtutorial.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.Student
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.Subject

data class SubjectsWithStudents(
    @Embedded val subject: Subject,
    @Relation(
        parentColumn = "subjectName",
        entityColumn = "studentName",
        associateBy = Junction(StudentSubjectCrossRef::class)
    )
    val students: List<Student>
)
