package org.bohdan.panum_lab5

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView


class StoreAdapter(private val stores: ArrayList<Store>) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_store,
        parent, false)
        return StoreViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stores.size
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val currentItem = stores[position]
        holder.textViewAddress.text = currentItem.address
        holder.textViewOpeningHours.text = currentItem.openingHours
        holder.textViewStoreName.text = currentItem.name

        Glide.with(holder.itemView.context)
            .load(currentItem.mapImageUrl)
            .into(holder.imageViewMap)

    }

    class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewOpeningHours: TextView = itemView.findViewById(R.id.textViewOpeningHours)
        val textViewStoreName: TextView = itemView.findViewById(R.id.textViewStoreName)
        val imageViewMap: ImageView = itemView.findViewById(R.id.imageViewMap)

    }
}
