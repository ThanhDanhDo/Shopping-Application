package com.example.shopping_application

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_application.Adapter.CartAdapter
import com.example.shopping_application.Helper.ChangeNumberItemsListener
import com.example.shopping_application.Helper.ManagmentCart
import com.example.shopping_application.databinding.ActivityCartBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managerCart: ManagmentCart
    private var tax: Double = 0.0
    private var feeTotal: Long = 0
    private var feeDelivery: Double = 0.0
    private var price: Long = 0
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    //SharedPreferences để lấy id người dùng hiện tại
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //khởi tạo database firebase
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        managerCart = ManagmentCart(this)

        setVariable()
        initCartList()
        calculateCart()

        binding.btnCheckOut.setOnClickListener {
            checkOut()
        }
    }

    private fun checkOut() {
        val userId = sharedPreferences.getString("userId", null)
        val orderId = UUID.randomUUID().toString() // Tạo ID đơn hàng duy nhất

        // Lấy danh sách sản phẩm từ giỏ hàng và chuyển đổi sang ItemOrderData
        val productsList = managerCart.getListCart().map { item ->
            EachProductOrderData(
                picUrl = item.selectedImageUrl ?: item.picUrl[0],
                productName = item.productName,
                quantityOfEach = item.numberInCart,
                size = item.selectedSize,
                feeEach = Math.round(item.numberInCart * item.price)
            )
        }

        val orderData = OrderData (orderId, price, feeTotal, feeDelivery, tax, productsList)

        val ordersRef = databaseReference.child(userId.toString()).child("orders")
        ordersRef.child(orderId).setValue(orderData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show()
                    // Dọn dẹp giỏ hàng sau khi đặt hàng thành công
                    managerCart.clearCart()
                } else {
                    Toast.makeText(this, "Failed to place order: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

        finish()
    }

    private fun initCartList() {
        binding.rvCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvCart.adapter =
            CartAdapter(managerCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }
            })

        with(binding) {
            txtEmpty.visibility =
                if (managerCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            svListCart.visibility =
                if (managerCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = Math.round((managerCart.getTotalFee() * percentTax) * 100) / 100.0
        val total = Math.round((managerCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = Math.round(managerCart.getTotalFee() * 100) / 100

        with(binding) {
            txtPrice.text = "$ $itemTotal"
            txtTax.text = "$ $tax"
            txtFeeDelivery.text = "$ $delivery"
            txtTotal.text = "$ $total"
        }

        price = itemTotal
        feeDelivery = delivery
        feeTotal = total
    }

    private fun setVariable() {
        binding.btnBack.setOnClickListener { finish() }
    }
}