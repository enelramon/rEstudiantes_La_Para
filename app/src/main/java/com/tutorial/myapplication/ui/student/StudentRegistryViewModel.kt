package com.tutorial.myapplication.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.myapplication.model.Student
import com.tutorial.myapplication.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentRegistryViewModel(private val repository: StudentRepository) : ViewModel() {
    fun insertStudent(student: Student) = viewModelScope.launch {
        repository.insertStudent(student)
    }
}