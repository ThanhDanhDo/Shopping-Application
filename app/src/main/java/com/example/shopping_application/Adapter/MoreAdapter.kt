package com.example.shopping_application.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopping_application.EachProductOrderData
import com.example.shopping_application.Model.ItemsModel
import com.example.shopping_application.OrderData
import com.example.shopping_application.databinding.ViewholderMoreBinding

class MoreAdapter (
    private var productList: MutableList<EachProductOrderData>,
    private var context: Context
) : RecyclerView.Adapter<MoreAdapter.ViewHolder>() {
    class ViewHolder (val binding: ViewholderMoreBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreAdapter.ViewHolder {
        val binding = ViewholderMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]

        Glide.with(holder.itemView.context)
            .load(product.picUrl)
            .into(holder.binding.imgProduct)

        holder.binding.txtProductName.text = product.productName
        holder.binding.txtFeeEach.text = "$ ${product.feeEach}"
        if (product.size == null) {
            holder.binding.LinearLayoutSize.visibility = View.GONE
        }
        else {
            holder.binding.txtSize.text = product.size
        }
        holder.binding.txtQuantity.text = product.quantityOfEach.toString()
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
