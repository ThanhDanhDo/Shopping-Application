package com.example.shopping_application.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.shopping_application.Helper.ChangeNumberItemsListener
import com.example.shopping_application.Helper.ManagmentCart
import com.example.shopping_application.Model.ItemsModel
import com.example.shopping_application.databinding.ViewholderCartBinding

class CartAdapter  (
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener?=null):RecyclerView.Adapter<CartAdapter.ViewHolder>(){

    class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private val managementCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = listItemSelected[position]

        holder.binding.txtProductName.text = item.productName
        holder.binding.txtPriceEachItem.text = "$ ${item.price}"
        holder.binding.txtTotalEachItem.text = "$ ${Math.round(item.numberInCart*item.price)}"
        holder.binding.txtNumberEachItem.text = item.numberInCart.toString()
        if (item.size.isNotEmpty()) {
            holder.binding.txtSize.text = item.selectedSize
        }
        else {
            holder.binding.LinearLayoutSize.visibility = View.GONE
        }


        Glide.with(holder.itemView.context)
            .load(item.selectedImageUrl ?: item.picUrl[0]) // Hiển thị ảnh đã chọn, nếu không có thì hiển thị ảnh mặc định
            .into(holder.binding.imgProduct)

        holder.binding.btnPlusCart.setOnClickListener {
            managementCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }

        holder.binding.btnMinusCart.setOnClickListener {
            managementCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return  listItemSelected.size
    }
}