package org.bohdan.panum_lab6

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import org.bohdan.panum_lab6.databinding.ActivityFoodBinding

class FoodActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        dbHelper = DatabaseHelper(this)

        val foodId = intent.getIntExtra("foodId", -1)
        val food = dbHelper.getFoodById(foodId)

        val txtFoodName:TextView = findViewById(R.id.txtName)
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        val txtPrice = findViewById<TextView>(R.id.txtFoodPrice)
        val imgFood = findViewById<ImageView>(R.id.img)

        txtFoodName.text = food.name
        txtDescription.text = food.description
        txtPrice.text = "$${food.price}"
        Glide.with(this)
            .load(food.imageUrl)
            .into(imgFood)
    }

}