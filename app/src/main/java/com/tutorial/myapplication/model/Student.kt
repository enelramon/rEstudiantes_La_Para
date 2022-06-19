package com.tutorial.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val semester: Int,
    val nationality: String
)
