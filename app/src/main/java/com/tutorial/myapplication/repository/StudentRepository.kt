package com.tutorial.myapplication.repository

import com.tutorial.myapplication.data.dao.StudentDao
import com.tutorial.myapplication.model.Student

class StudentRepository(private val dao: StudentDao) {
    suspend fun insertStudent(student: Student) {
        dao.insertStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        dao.updateStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        dao.deleteStudent(student)
    }

    fun getStudent(id: Int) = dao.getStudent(id)

    fun getAll() = dao.getAll()

}