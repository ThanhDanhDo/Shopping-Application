package com.example.shopping_application

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_application.Adapter.OrderAdapter
import com.example.shopping_application.databinding.ActivityOrderBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    private var orderList: MutableList<OrderData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //khởi tạo database firebase
        firebaseDatabase = FirebaseDatabase.getInstance()

        initOrderList()

        binding.btnBack.setOnClickListener {finish()}
    }

    private fun initOrderList() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        val ordersRef = firebaseDatabase.getReference("users").child(userId.toString()).child("orders")

        binding.rvOrder.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = OrderAdapter(orderList, this)
        binding.rvOrder.adapter = adapter

        // Lấy dữ liệu từ Firebase
        ordersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(OrderData::class.java)
                    if (order != null) {
                        orderList.add(order)
                    }
                }
                adapter.notifyDataSetChanged()  // Đảm bảo gọi để cập nhật danh sách

                if (orderList.isEmpty()) {
                    binding.txtEmpty.visibility = View.VISIBLE
                    binding.rvOrder.visibility = View.GONE
                } else {
                    binding.txtEmpty.visibility = View.GONE
                    binding.rvOrder.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@OrderActivity, "Failed to load orders: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}