package com.example.myapplication.repository

import com.example.myapplication.api.DrinksApi


class DrinksRepository constructor(private val retrofitService: DrinksApi){

    fun getDrinksByName(name:String) = retrofitService.getDrinksByName(name)

    fun getDrinksByStartingAlphabet(alphabet:Char) = retrofitService.getDrinksByStartingAlphabet(alphabet)

}