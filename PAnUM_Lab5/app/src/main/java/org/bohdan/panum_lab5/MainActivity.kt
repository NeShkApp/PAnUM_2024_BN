package org.bohdan.panum_lab5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var stores: ArrayList<Store>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stores = arrayListOf(
            Store("Store 1", "Address 1", "9AM-6PM", "https://nolinskiparis.com/wp-content/uploads/2022/06/restaurant-nolinski-paris-5-etoiles-luxe-12-guillaume-de-laubier.jpg"),
            Store("Store 2", "Address 2", "10AM-7PM", "https://panoramicrestaurant.com/wp-content/uploads/2023/07/2TH08812-1-scaled.jpg"),
            Store("Store 3", "Address 3", "8AM-5PM", "https://cdn.vox-cdn.com/thumbor/5d_RtADj8ncnVqh-afV3mU-XQv0=/0x0:1600x1067/1200x900/filters:focal(672x406:928x662)/cdn.vox-cdn.com/uploads/chorus_image/image/57698831/51951042270_78ea1e8590_h.7.jpg")
        )

        newRecyclerView = findViewById(R.id.recyclerViewStores)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter = StoreAdapter(stores)

    }
}
