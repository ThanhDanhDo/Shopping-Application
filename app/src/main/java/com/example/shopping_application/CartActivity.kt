package com.example.shopping_application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_application.Adapter.CartAdapter
import com.example.shopping_application.Helper.ChangeNumberItemsListener
import com.example.shopping_application.Helper.ManagmentCart
import com.example.shopping_application.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managerCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managerCart = ManagmentCart(this)

        setVariable()
        initCartList()
        calculateCart()
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
    }

    private fun setVariable() {
        binding.btnBack.setOnClickListener { finish() }
    }
}