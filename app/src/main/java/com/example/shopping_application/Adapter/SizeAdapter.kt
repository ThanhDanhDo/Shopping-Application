package com.example.shopping_application.Adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopping_application.DetailActivity
import com.example.shopping_application.Model.ProductTypeModel
import com.example.shopping_application.R
import com.example.shopping_application.databinding.ViewholderListProductBinding
import com.example.shopping_application.databinding.ViewholderProductTypeBinding
import com.example.shopping_application.databinding.ViewholderSizeBinding

class SizeAdapter(
    val items: MutableList<String>,
    // Truyền listener vào constructor
    private val onSizeSelected: (String) -> Unit
) : RecyclerView.Adapter<SizeAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    class Viewholder(val binding: ViewholderSizeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SizeAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: SizeAdapter.Viewholder, position: Int) {
        holder.binding.txtSize.text = items[position]

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            onSizeSelected(items[position]) // Gọi listener để thông báo kích thước đã chọn

        }

        if (selectedPosition == position) {
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.grey_bg_selected)
            holder.binding.txtSize.setTextColor(context.resources.getColor(R.color.darkGreen))
        } else {
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.txtSize.setTextColor(context.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}