package com.example.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.Favourite

@Database(
    entities = [Favourite::class],
    version = 1
)
abstract class DrinkDatabase:RoomDatabase() {
    abstract fun getDatabaseDao():Dao
    companion object{
        @Volatile
        private var instance: DrinkDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?:synchronized(LOCK){
            instance?: createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,DrinkDatabase::class.java,"favourite.db").build()
    }
}