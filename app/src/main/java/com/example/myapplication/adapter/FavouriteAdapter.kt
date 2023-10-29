package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.Favourite
import com.example.myapplication.databinding.ItemLayoutBinding

class FavouriteAdapter(context: Context, onFavouriteClickListener: OnFavouriteClickListener) : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    private var favouriteList = ArrayList<Favourite>()
    private var context = context
    private var listener:OnFavouriteClickListener = onFavouriteClickListener


    @SuppressLint("NotifyDataSetChanged")
    fun setDrinkList(drinkList : List<Favourite>){
        this.favouriteList = drinkList as ArrayList<Favourite>
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
            favouriteList[position].let {item->
                Glide.with(holder.itemView)
                    .load(item.imageData)
                    .into(itemImage)
                itemName.text = item.drinkName
                itemDescription.text = item.drinkDescription
                checkbox.isChecked = item.isAlcohol
                favouriteImage.setImageResource(R.drawable.selected_start)
                favouriteImage.setOnClickListener {
                    var isFavourite = item.isFavourite
                    isFavourite = if(isFavourite) {
                        favouriteImage.setImageResource(R.drawable.selected_start)
                        false
                    } else {
                        true
                    }
                    val favourite = Favourite(drinkName = item.drinkName, drinkDescription = item.drinkDescription, isAlcohol = checkbox.isChecked, imageData = item.imageData, drinkId = item.drinkId, isFavourite =isFavourite)
                    listener.OnFavouriteListener(favourite)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return favouriteList.size
    }
}
