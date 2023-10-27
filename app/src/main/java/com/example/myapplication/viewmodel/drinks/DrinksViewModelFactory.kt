package com.example.myapplication.viewmodel.drinks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.DrinksRepository

class DrinksViewModelFactory(private val repository: DrinksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinksViewModel::class.java)) {
            return DrinksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}