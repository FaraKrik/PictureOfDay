package com.example.pictureofdays.main.model.Repository

import com.example.pictureofdays.main.model.Photo

interface IRepository {

    fun photoDay(callback: (result: RepositoryResult<Photo>) -> Unit)
}