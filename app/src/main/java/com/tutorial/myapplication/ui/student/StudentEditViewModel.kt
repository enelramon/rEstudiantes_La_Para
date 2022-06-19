package com.tutorial.myapplication.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tutorial.myapplication.model.Student
import com.tutorial.myapplication.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentEditViewModel(private val repository: StudentRepository) : ViewModel() {
    fun updateStudent(student: Student) = viewModelScope.launch {
        repository.updateStudent(student)
    }

    fun getStudent(id: Int) = repository.getStudent(id)
}