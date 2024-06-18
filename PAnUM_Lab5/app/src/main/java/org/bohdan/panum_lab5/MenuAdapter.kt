package org.bohdan.panum_lab5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MenuAdapter(private val foodItems: List<FoodItem>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return foodItems.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = foodItems[position]
        holder.textViewFoodName.text = currentItem.name
        holder.textViewFoodPrice.text = currentItem.price.toString()

        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .into(holder.imageViewFood)
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewFoodName: TextView = itemView.findViewById(R.id.textViewFoodName)
        val textViewFoodPrice: TextView = itemView.findViewById(R.id.textViewFoodPrice)
        val imageViewFood: ImageView = itemView.findViewById(R.id.imageViewFood)
    }
}
