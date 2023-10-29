package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.DrinksAdapter
import com.example.myapplication.adapter.OnFavouriteClickListener
import com.example.myapplication.data.Favourite
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.repository.DatabaseRepository
import com.example.myapplication.room.Dao
import com.example.myapplication.room.DrinkDatabase
import com.example.myapplication.viewmodel.drinks.DrinksViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(),OnFavouriteClickListener {
    private lateinit var viewModel:DrinksViewModel
    private lateinit var binding : FragmentHomeBinding
    private var mAdapter: DrinksAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DrinksViewModel::class.java]
        populateDrinkRecyclerView()
        setObservers()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.searchCocktailByName("margarita")
        }
        viewModel.cocktailData.observe(viewLifecycleOwner) {
            mAdapter?.setDrinkList(it.drinks)
        }
        binding.pageTitle.text = resources.getText(R.string.drinkrecipes)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(binding.radioBtnSearchByName.isChecked)
                {
                    CoroutineScope(Dispatchers.IO).launch {
                        query?.let {
                            if(query.isNotEmpty()){
                                viewModel.searchCocktailByName(it)
                            }
                        }
                    }
                }
                else{
                    CoroutineScope(Dispatchers.IO).launch {
                        query?.let {
                            if(query.isNotEmpty()){
                                viewModel.searchCocktailByAlphabet(it.first())
                            }
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return binding.root
    }
    private fun populateDrinkRecyclerView(){
        binding.homeRecyclerview.layoutManager = LinearLayoutManager(context)
        mAdapter = DrinksAdapter(requireContext(),this)
        binding.homeRecyclerview.adapter = mAdapter
        binding.homeRecyclerview.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        binding.homeRecyclerview.itemAnimator = DefaultItemAnimator()
    }

    override fun OnFavouriteListener(item: Favourite) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                DatabaseRepository(DrinkDatabase(requireContext())).insert(item)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Added To Favorite",Toast.LENGTH_SHORT).show()
                }
            }.onFailure {
                Log.d("Error",it.toString())
            }
        }
    }
    private fun setObservers(){
        viewModel.uiState.observe(viewLifecycleOwner) {
            val dataState = it ?: return@observe
            binding.progressbar.visibility = if (dataState.isLoading) View.VISIBLE else View.GONE
            Log.d("Error:",it.error.toString())
            }
        }
}