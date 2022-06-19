package com.tutorial.myapplication.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tutorial.myapplication.repository.StudentRepository

class StudentRegistryViewModelFactory(private val repository: StudentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StudentRegistryViewModel(repository) as T
    }
}