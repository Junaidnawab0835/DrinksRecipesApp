package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.api.DrinksApi
import com.example.myapplication.repository.DrinksRepository
import com.example.myapplication.viewmodel.drinks.DrinksViewModel
import com.example.myapplication.viewmodel.drinks.DrinksViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var viewModel: DrinksViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val repository = DrinksRepository(DrinksApi.api) // Initialize your repository
        viewModel = ViewModelProvider(this, DrinksViewModelFactory(repository))[DrinksViewModel::class.java]
//        viewModel = ViewModelProvider(this )[DrinksViewModel::class.java]
        val list = viewModel.searchCocktailByName("margarita")
        Log.d("test123",list.toString())
        return view
    }

}