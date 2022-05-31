package com.example.pictureofdays.ui.extensions.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pictureofdays.main.model.Repository.IRepository

class HomeViewModelFactory(
    private val repository: IRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}