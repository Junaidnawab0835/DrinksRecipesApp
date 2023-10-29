package com.example.myapplication.repository


import com.example.myapplication.data.Favourite
import com.example.myapplication.room.DrinkDatabase
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val db:DrinkDatabase) {
    suspend fun insert(item: Favourite) = db.getDatabaseDao().insertItem(item)

    suspend fun delete(item: Favourite) = db.getDatabaseDao().deleteItem(item)

    suspend fun getAllItems() = db.getDatabaseDao().getAllFavouriteDrinks()

}