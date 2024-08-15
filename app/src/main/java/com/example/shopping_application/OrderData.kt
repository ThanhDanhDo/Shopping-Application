package com.example.shopping_application

data class OrderData(
    val orderId: String? = null,
    val price: Long ?= null,
    val feeTotal: Long? = null,
    val feeDelivery: Double ?= null,
    val tax: Double ?= null,
    val products: List<EachProductOrderData>? = null
)
