package com.example.myapplication.viewmodel.drinks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.DrinkApiResponse
import com.example.myapplication.repository.DrinksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DrinksViewModel @Inject constructor(private val repository: DrinksRepository): ViewModel() {
    private val _cocktailData = MutableLiveData<DrinkApiResponse>()
    val cocktailData: LiveData<DrinkApiResponse> get() = _cocktailData

    private val previewDataState = MutableLiveData<UiPreviewDataState>()
    val uiState: LiveData<UiPreviewDataState> get() = previewDataState


    suspend fun searchCocktailByName(query: String) {
            emitImagePreviewUiState(isLoading = true)
            val response = repository.getDrinksByName(query)
            response.enqueue(object : Callback<DrinkApiResponse>{
                override fun onResponse(call: Call<DrinkApiResponse>, response: Response<DrinkApiResponse>) {
                    emitImagePreviewUiState(isLoading = false)
                    if (response.isSuccessful) {
                        if(!response.body()?.drinks.isNullOrEmpty()) {
                            _cocktailData.postValue(response.body())
                        }
                        else
                        {
                            emitImagePreviewUiState(error = "Error Api Response")
                        }
                    } else {
                        emitImagePreviewUiState(error = "Error Api Response")
                    }
                }
                override fun onFailure(call: Call<DrinkApiResponse>, t: Throwable) {
                    emitImagePreviewUiState(error = t.message)
                    emitImagePreviewUiState(isLoading = false)
                }
            })
    }

    suspend fun searchCocktailByAlphabet(alphabet: Char) {
        emitImagePreviewUiState(isLoading = true)
        val response = repository.getDrinksByStartingAlphabet(alphabet)
        response.enqueue(object : Callback<DrinkApiResponse>{
            override fun onResponse(call: Call<DrinkApiResponse>, response: Response<DrinkApiResponse>) {
                emitImagePreviewUiState(isLoading = false)
                if (response.isSuccessful) {
                    _cocktailData.postValue(response.body())
                } else {
                    emitImagePreviewUiState(error = "Error Api Response")
                    emitImagePreviewUiState()
                }
            }
            override fun onFailure(call: Call<DrinkApiResponse>, t: Throwable) {
                emitImagePreviewUiState(error = t.message)
                emitImagePreviewUiState(isLoading = false)
            }
        })
    }

    private fun emitImagePreviewUiState(
        isLoading: Boolean = false,
        error: String? = null,
    )
    {
        val dataState = UiPreviewDataState(isLoading, error)
        previewDataState.postValue(dataState)
    }

    data class UiPreviewDataState(
        val isLoading: Boolean,
        val error: String?,
    )


}

