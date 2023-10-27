package com.example.myapplication.viewmodel.drinks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Drink
import com.example.myapplication.repository.DrinksRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class DrinksViewModel constructor(private val repository: DrinksRepository): ViewModel() {
    private val _cocktailData = MutableLiveData<List<Drink>>()
    val errorMessage = MutableLiveData<String>()

    fun searchCocktailByName(query: String) {
        val response = repository.getDrinksByName(query)
        response.enqueue(object : Callback<List<Drink>> {
            override fun onResponse(call: Call<List<Drink>>, response: Response<List<Drink>>) {
                if (response.isSuccessful) {
                    _cocktailData.postValue(response.body())
                } else {
                    errorMessage.postValue("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Drink>>, t: Throwable) {
                errorMessage.postValue("Error: ${t.message}")
            }
        })

    }

    fun searchCocktailByAlphabet(alphabet: Char) {
        viewModelScope.launch {
            repository.getDrinksByStartingAlphabet(alphabet)
        }
    }


}

