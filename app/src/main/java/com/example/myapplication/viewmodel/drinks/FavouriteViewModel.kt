package com.example.myapplication.viewmodel.drinks

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DrinkApiResponse
import com.example.myapplication.data.Favourite
import com.example.myapplication.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private var databaseRepository: DatabaseRepository):ViewModel() {
    private val _favouriteDrinksData = MutableLiveData<List<Favourite>>()
    val favouriteDrinkData: LiveData<List<Favourite>> get() = _favouriteDrinksData
    private val previewDataState = MutableLiveData<UiPreviewDataState>()
    val uiState: LiveData<UiPreviewDataState> get() = previewDataState

    fun insert(items: Favourite) = CoroutineScope(Dispatchers.IO).launch {
        databaseRepository.insert(items)
    }

    fun delete(items: Favourite) = CoroutineScope(Dispatchers.IO).launch {
        databaseRepository.delete(items)
    }

    fun getAllItem(){
        emitImagePreviewUiState(isLoading = true)
        CoroutineScope(Dispatchers.IO).launch{
            kotlin.runCatching {
                _favouriteDrinksData.postValue(databaseRepository.getAllItems())
            }.onSuccess {
                emitImagePreviewUiState(isLoading = false)
            }.onFailure {
                emitImagePreviewUiState(error = it.toString())
            }
        }
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
