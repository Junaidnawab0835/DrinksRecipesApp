package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.utilities.Constants
import com.example.myapplication.api.DrinksApi
import com.example.myapplication.repository.DatabaseRepository
import com.example.myapplication.repository.DrinksRepository
import com.example.myapplication.room.Dao
import com.example.myapplication.room.DrinkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideApi():DrinksApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DrinksApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(api: DrinksApi):DrinksRepository {
        return DrinksRepository(api)
    }

    @Provides
    fun provideDrinkDatabase(@ApplicationContext context: Context): DrinkDatabase {
        return Room.databaseBuilder(context.applicationContext,DrinkDatabase::class.java,"favourite.db").build()
    }

    @Provides
    fun provideDrinkDao(database: DrinkDatabase): Dao {
        return database.getDatabaseDao()
    }

    @Provides
    fun provideDatabaseRepository(db: DrinkDatabase):DatabaseRepository {
        return DatabaseRepository(db)
    }
}