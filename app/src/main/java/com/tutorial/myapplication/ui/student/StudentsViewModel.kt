package com.tutorial.myapplication.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.myapplication.model.Student
import com.tutorial.myapplication.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentsViewModel(private val repository: StudentRepository) : ViewModel() {
    fun delete(student:Student) = viewModelScope.launch {
        repository.deleteStudent(student)
    }

    fun getAll() = repository.getAll()
}