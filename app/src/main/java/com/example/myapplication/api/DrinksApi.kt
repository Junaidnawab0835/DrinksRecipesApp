package com.example.myapplication.api

import com.example.myapplication.data.Drink
import com.example.myapplication.data.DrinkApiResponse
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinksApi {
    @GET("search.php")
    fun getDrinksByName(
        @Query("s") name: String
    ): Call<DrinkApiResponse>

    @GET("search.php")
    fun getDrinksByStartingAlphabet(
        @Query("f") alphabet: Char
    ): Call<DrinkApiResponse>

}