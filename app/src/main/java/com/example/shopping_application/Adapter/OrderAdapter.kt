package com.example.shopping_application.Adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.shopping_application.Adapter.CartAdapter.ViewHolder
import com.example.shopping_application.EachProductOrderData
import com.example.shopping_application.Helper.ChangeNumberItemsListener
import com.example.shopping_application.Model.ItemsModel
import com.example.shopping_application.MoreActivity
import com.example.shopping_application.OrderData
import com.example.shopping_application.databinding.ViewholderCartBinding
import com.example.shopping_application.databinding.ViewholderOrderBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderAdapter (
private var orderList: MutableList<OrderData>,
private var context: Context
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private lateinit var feeTotalReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
    }

    class ViewHolder(val binding: ViewholderOrderBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        val binding = ViewholderOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        val orderID = order.orderId

        // Lấy userId từ SharedPreferences
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        // Truy cập vào feeTotal
        feeTotalReference = firebaseDatabase.getReference("users/$userId/orders/$orderID/feeTotal")
        feeTotalReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feeTotal = snapshot.getValue(Double::class.java) ?: 0.0
                holder.binding.txtFeeTotal.text = feeTotal.toString()  // Gán giá trị feeTotal vào TextView
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        // Hiển thị sản phẩm đầu tiên trong đơn hàng
        val firstItem = order.products?.get(0)

        Glide.with(holder.itemView.context)
            .load(firstItem?.picUrl)
            .into(holder.binding.imgProduct)

        holder.binding.txtProductName.text = firstItem?.productName
        holder.binding.txtFeeEach.text = "$${firstItem?.feeEach}"
        holder.binding.txtSize.text = firstItem?.size
        holder.binding.txtQuantity.text = firstItem?.quantityOfEach.toString()

        holder.binding.btnMore.setOnClickListener {
            val intent = Intent(context, MoreActivity::class.java)
            intent.putExtra("orderID", orderID) // Truyền orderID
            context.startActivity(intent)
        }
    }

    private fun loadFeeTotal() {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}