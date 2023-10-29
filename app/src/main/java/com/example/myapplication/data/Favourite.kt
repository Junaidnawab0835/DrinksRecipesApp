package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("DB_FAVOURITE")
data class Favourite(
    @PrimaryKey()
    val drinkId:String,
    @ColumnInfo("drink_name")
    val drinkName:String,
    @ColumnInfo("drink_description")
    val drinkDescription:String,
    @ColumnInfo("alcohol")
    val isAlcohol:Boolean,
    @ColumnInfo("favourite")
    val isFavourite:Boolean,
    @ColumnInfo(name = "drink_image")
    val imageData: ByteArray?
)