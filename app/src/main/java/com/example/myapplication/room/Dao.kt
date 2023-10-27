package com.example.myapplication.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.Drink


@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertItem(items: Drink)

    @Delete()
    suspend fun deleteItem(items: Drink)

    @Query("SELECT * FROM DB_FAVOURITE")
    suspend fun getAllFavouriteDrinks():LiveData<List<Drink>>

}