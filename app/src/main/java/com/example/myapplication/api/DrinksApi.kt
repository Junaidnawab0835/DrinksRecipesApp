package com.example.myapplication.api

import com.example.myapplication.data.Drink
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinksApi {
    companion object{
        val Base_URl = "https://www.thecocktaildb.com/api/json/v1/1/"
        val api : DrinksApi by lazy {
            Retrofit.Builder()
                .baseUrl(Base_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DrinksApi::class.java)
        }
    }
    @GET("search.php")
    fun getDrinksByName(
        @Query("s") name: String
    ): Call<List<Drink>>

    @GET("search.php")
    fun getDrinksByStartingAlphabet(
        @Query("f") alphabet: Char
    ): Call<List<Drink>>

}