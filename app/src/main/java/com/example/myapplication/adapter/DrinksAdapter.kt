package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.api.DrinksApi
import com.example.myapplication.data.Drink
import com.example.myapplication.databinding.ItemLayoutBinding

class DrinksAdapter: RecyclerView.Adapter<DrinksAdapter.ViewHolder>() {
    private var drinkList = ArrayList<Drink>()
    @SuppressLint("NotifyDataSetChanged")
    fun setDrinkList(movieList : List<Drink>){
        this.drinkList = movieList as ArrayList<Drink>
        notifyDataSetChanged()
    }
    class ViewHolder(val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)  {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(DrinksApi.Base_URl+drinkList[position].strDrinkThumb)
            .into(holder.binding.itemImage)
        holder.binding.itemName.text = drinkList[position].strDrink
        holder.binding.itemDescription.text = drinkList[position].strInstructions
        holder.binding.checkbox.isChecked = drinkList[position].strAlcoholic == "Alcoholic"
        holder.binding.favouriteImage.setOnClickListener {

        }
    }
    override fun getItemCount(): Int {
        return drinkList.size
    }
}