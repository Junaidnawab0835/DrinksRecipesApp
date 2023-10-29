package com.example.myapplication.room


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.Favourite


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(items: Favourite)

    @Delete()
    fun deleteItem(items: Favourite)

    @Query("SELECT * FROM DB_FAVOURITE")
    fun getAllFavouriteDrinks():List<Favourite>

    @Query("SELECT COUNT() FROM DB_FAVOURITE WHERE drinkId = :id")
    fun checkExist(id: String): Int

}