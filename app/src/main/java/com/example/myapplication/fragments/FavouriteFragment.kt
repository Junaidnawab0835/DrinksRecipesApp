package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.FavouriteAdapter
import com.example.myapplication.adapter.OnFavouriteClickListener
import com.example.myapplication.data.Favourite
import com.example.myapplication.databinding.FragmentFavouriteBinding
import com.example.myapplication.repository.DatabaseRepository
import com.example.myapplication.room.DrinkDatabase
import com.example.myapplication.viewmodel.drinks.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavouriteFragment : Fragment(),OnFavouriteClickListener {
    private lateinit var binding: FragmentFavouriteBinding
    private var mAdapter: FavouriteAdapter? = null
    private lateinit var viewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        populateFavouriteRecyclerView()
        refreshData()
        setObservers()
        binding.pageTitle.text = resources.getText(R.string.favourite_recipe)
        viewModel.favouriteDrinkData.observe(viewLifecycleOwner){
            if(it!=null)
            {
                mAdapter!!.setDrinkList(it)
            }
        }

        return binding.root
    }

    private fun refreshData(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getAllItem()
        }
    }

    private fun populateFavouriteRecyclerView() {
        binding.favouriteRecyclerview.layoutManager = LinearLayoutManager(context)
        mAdapter = FavouriteAdapter(requireContext(), this)
        binding.favouriteRecyclerview.adapter = mAdapter
        binding.favouriteRecyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.favouriteRecyclerview.itemAnimator = DefaultItemAnimator()
    }

    override fun OnFavouriteListener(item: Favourite) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                if(!item.isFavourite) {
                    viewModel.delete(item)
                }
            }.onSuccess {
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Item Deleted Successfully",Toast.LENGTH_SHORT).show()
                    refreshData()
                }
            }.onFailure {
                Log.d("Error:",it.toString())
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