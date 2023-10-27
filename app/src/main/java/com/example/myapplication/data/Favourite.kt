package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("DB_FAVOURITE")
data class Favourite (
    @PrimaryKey(true)
    val drinkId:Long = 0,
    @ColumnInfo("drink_name")
    val drinkName:String,
    @ColumnInfo("drink_title")
    val drinkTitle:String,
    @ColumnInfo("drink_image")
        val drinkImage:String,
    @ColumnInfo("alcohol")
    val isAlcohol:Boolean
)