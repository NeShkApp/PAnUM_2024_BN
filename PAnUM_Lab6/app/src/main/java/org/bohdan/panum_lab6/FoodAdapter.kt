package org.bohdan.panum_lab6

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoodAdapter(private val foodList: List<Food>): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentFood = foodList[position]
        holder.nameTextView.text = currentFood.name
        holder.priceTextView.text = "$${currentFood.price}"
        Glide.with(holder.itemView.context)
            .load(currentFood.imageUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, FoodActivity::class.java)
            intent.putExtra("foodId", position+1)
            holder.itemView.context.startActivity(intent)
        }
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.txtFoodName)
        val priceTextView: TextView = itemView.findViewById(R.id.txtPrice)
        val imageView: ImageView = itemView.findViewById(R.id.imgFood)
    }
}