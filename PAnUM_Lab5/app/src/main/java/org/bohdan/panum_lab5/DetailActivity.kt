package org.bohdan.panum_lab5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val storeName = intent.getStringExtra("storeName")
        val address = intent.getStringExtra("address")
        val openingHours = intent.getStringExtra("openingHours")
        val mapImageUrl = intent.getStringExtra("mapImageUrl")

        val textViewDetailStoreName: TextView = findViewById(R.id.textViewDetailStoreName)
        val textViewDetailAddress: TextView = findViewById(R.id.textViewDetailAddress)
        val textViewDetailOpeningHours: TextView = findViewById(R.id.textViewDetailOpeningHours)
        val imageViewDetailMap: ImageView = findViewById(R.id.imageViewDetailMap)
        val recyclerViewMenu: RecyclerView = findViewById(R.id.recyclerViewMenu)

        textViewDetailStoreName.text = storeName
        textViewDetailAddress.text = address
        textViewDetailOpeningHours.text = openingHours

        Glide.with(this)
            .load(mapImageUrl)
            .into(imageViewDetailMap)

        val menuItems = intent.getSerializableExtra("menuItems") as ArrayList<FoodItem>

        recyclerViewMenu.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMenu.adapter = MenuAdapter(menuItems)
    }
}
