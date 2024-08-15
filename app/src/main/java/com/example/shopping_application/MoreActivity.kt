package com.example.shopping_application

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_application.Adapter.MoreAdapter
import com.example.shopping_application.databinding.ActivityMoreBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoreBinding
    private lateinit var productList: MutableList<EachProductOrderData>
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var priceReference: DatabaseReference
    private lateinit var feeDeliveryReference: DatabaseReference
    private lateinit var taxReference: DatabaseReference
    private lateinit var feeTotalReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy userId từ SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        productList = mutableListOf()

        val orderID = intent.getStringExtra("orderID")
        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("users/$userId/orders/$orderID/products")
        loadProductData()

        binding.rvMore.adapter = MoreAdapter(productList, this)
        binding.rvMore.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Truy cập vào feeTotal
        feeTotalReference = firebaseDatabase.getReference("users/$userId/orders/$orderID/feeTotal")
        priceReference = firebaseDatabase.getReference("users/$userId/orders/$orderID/price")
        feeDeliveryReference = firebaseDatabase.getReference("users/$userId/orders/$orderID/feeDelivery")
        taxReference = firebaseDatabase.getReference("users/$userId/orders/$orderID/tax")

        loadFee()

        binding.btnBack.setOnClickListener { finish() }

    }

    private fun loadFee() {
        feeTotalReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feeTotal = snapshot.getValue(Double::class.java) ?: 0.0
                binding.txtTotal.text = feeTotal.toString()  // Gán giá trị feeTotal vào TextView
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MoreActivity, "Failed to load fee total: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        priceReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val price = snapshot.getValue(Double::class.java) ?: 0.0
                binding.txtPrice.text = price.toString()  // Gán giá trị
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MoreActivity, "Failed to load price: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        feeDeliveryReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feeDelivery = snapshot.getValue(Double::class.java) ?: 0.0
                binding.txtFeeDelivery.text = feeDelivery.toString()  // Gán giá trị
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MoreActivity, "Failed to load fee delivery: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        taxReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tax = snapshot.getValue(Double::class.java) ?: 0.0
                binding.txtTax.text = tax.toString()  // Gán giá trị feeTotal vào TextView
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MoreActivity, "Failed to load fee total: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadProductData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(EachProductOrderData::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
                binding.rvMore.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MoreActivity, "Failed to load products: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}