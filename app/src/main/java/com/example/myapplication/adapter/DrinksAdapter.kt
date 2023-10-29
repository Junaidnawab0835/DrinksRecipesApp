package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.utilities.FileHelper
import com.example.myapplication.R
import com.example.myapplication.data.Drink
import com.example.myapplication.data.Favourite
import com.example.myapplication.databinding.ItemLayoutBinding
import com.example.myapplication.room.DrinkDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrinksAdapter(context: Context,onFavouriteClickListener: OnFavouriteClickListener) : RecyclerView.Adapter<DrinksAdapter.ViewHolder>() {
    private var drinkList = ArrayList<Drink>()
    private var context = context
    private var listener:OnFavouriteClickListener = onFavouriteClickListener

    @SuppressLint("NotifyDataSetChanged")
    fun setDrinkList(drinkList : List<Drink>?){
        this.drinkList = drinkList as ArrayList<Drink>
        notifyDataSetChanged()
    }
    class ViewHolder(val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

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
        holder.binding.apply {
            drinkList[position].let {item->
                Glide.with(holder.itemView)
                    .load(item.strDrinkThumb)
                    .into(itemImage)
                itemName.text = item.strDrink
                itemDescription.text = item.strInstructions
                checkbox.isChecked = item.strAlcoholic == "Alcoholic"
                CoroutineScope(Dispatchers.IO).launch {
                    if(DrinkDatabase(context).getDatabaseDao().checkExist(item.idDrink)>0)
                    {
                        withContext(Dispatchers.Main){
                            favouriteImage.setImageResource(R.drawable.selected_start)
                        }
                    }
                }
                favouriteImage.setOnClickListener {
                    favouriteImage.setImageResource(R.drawable.selected_start)
                    CoroutineScope(Dispatchers.IO).launch {
                            FileHelper.loadImageBytesFromUrl(item.strDrinkThumb).let { image->
                                val favourite = Favourite(drinkName = item.strDrink, drinkDescription = item.strInstructions, isAlcohol = checkbox.isChecked, imageData = image, isFavourite =true, drinkId = item.idDrink)
                                listener.OnFavouriteListener(favourite)
                        }
                    }

                }
            }
        }
    }
    override fun getItemCount(): Int {
        return drinkList.size
    }
}
