package com.example.pictureofdays.ui.extensions.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureofdays.main.model.Repository.Error
import com.example.pictureofdays.main.model.Photo
import com.example.pictureofdays.main.model.Repository.IRepository
import com.example.pictureofdays.main.model.Repository.Success

class HomeViewModel(private val repository: IRepository) : ViewModel() {

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _photoLiveData = MutableLiveData<Photo>()

    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val errorLiveData: LiveData<String?> = _errorLiveData
    val photoLiveData: LiveData<Photo> = _photoLiveData

    fun getPhoto() {
        _loadingLiveData.value = true
        repository.photoDay() {
            when (it) {
                is Success -> {
                    _photoLiveData.value = it.value ?: null
                    _errorLiveData.value = null
                    _loadingLiveData.value = false
                }
                is Error -> {
                    _errorLiveData.value = it.value.message.toString()
                    _loadingLiveData.value = false
                }
            }
        }
    }
}