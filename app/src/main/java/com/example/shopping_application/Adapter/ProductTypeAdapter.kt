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
import com.example.shopping_application.databinding.ViewholderProductTypeBinding

class ProductTypeAdapter(val item: MutableList<ProductTypeModel>) :
    RecyclerView.Adapter<ProductTypeAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    class Viewholder(val binding: ViewholderProductTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductTypeAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderProductTypeBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: ProductTypeAdapter.Viewholder, position: Int) {
        val item = item[position]
        holder.binding.txtTitle.text = item.title

        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.imgProductType)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        holder.binding.txtTitle.setTextColor(context.resources.getColor(R.color.white))
        if (selectedPosition == position) {
            holder.binding.imgProductType.setBackgroundResource(0)
            holder.binding.mainLayout.setBackgroundResource(R.drawable.dark_green_bg)
            ImageViewCompat.setImageTintList(holder.binding.imgProductType, ColorStateList.valueOf(context.getColor(R.color.white)))

            holder.binding.txtTitle.visibility = View.VISIBLE
        }
        else {
            holder.binding.imgProductType.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.mainLayout.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(holder.binding.imgProductType, ColorStateList.valueOf(context.getColor(R.color.black)))

            holder.binding.txtTitle.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }
}