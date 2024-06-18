package org.bohdan.panum_lab5

data class Store(
    val name: String,
    val address: String,
    val openingHours: String,
    val mapImageUrl: String,
    val menuItems: ArrayList<FoodItem>
)
