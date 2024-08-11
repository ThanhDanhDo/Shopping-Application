package com.example.shopping_application.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopping_application.Model.ProductTypeModel
import com.example.shopping_application.R
import com.example.shopping_application.databinding.ViewholderListProductBinding
import com.example.shopping_application.databinding.ViewholderProductTypeBinding

class ListProductAdapter(val items: MutableList<String>) :
    RecyclerView.Adapter<ListProductAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    class Viewholder(val binding: ViewholderListProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProductAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderListProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: ListProductAdapter.Viewholder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.imgProduct)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if (selectedPosition == position) {
            holder.binding.listProductLayout.setBackgroundResource(R.drawable.grey_bg_selected)
        }
        else {
            holder.binding.imgProduct.setBackgroundResource(R.drawable.grey_bg)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}