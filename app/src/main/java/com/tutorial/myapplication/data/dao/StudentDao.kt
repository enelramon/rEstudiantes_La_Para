package com.tutorial.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tutorial.myapplication.model.Student

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM Student WHERE id = :id")
    fun getStudent(id: Int): LiveData<Student>

    @Query("SELECT * FROM Student")
    fun getAll(): LiveData<List<Student>>
}