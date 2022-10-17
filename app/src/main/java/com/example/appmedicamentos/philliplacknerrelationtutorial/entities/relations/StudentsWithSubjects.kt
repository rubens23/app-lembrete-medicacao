package com.example.appmedicamentos.philliplacknerrelationtutorial.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.Student
import com.example.appmedicamentos.philliplacknerrelationtutorial.entities.Subject

data class StudentsWithSubjects(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentName",
        entityColumn = "subjectName",
        associateBy = Junction(StudentSubjectCrossRef::class)
    )
    val subjects: List<Subject>
)
