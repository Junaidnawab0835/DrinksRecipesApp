package com.example.myapplication.repository

import com.example.myapplication.api.DrinksApi
import com.example.myapplication.data.Drink
import com.example.myapplication.data.DrinkApiResponse
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


class DrinksRepository @Inject constructor(private val api: DrinksApi){

    suspend fun getDrinksByName(name:String) = api.getDrinksByName(name)

    suspend fun getDrinksByStartingAlphabet(alphabet:Char) = api.getDrinksByStartingAlphabet(alphabet)

}