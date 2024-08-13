package com.example.shopping_application.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_application.Model.ItemsModel
import com.example.shopping_application.databinding.ViewholderRecommendationBinding
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.shopping_application.DetailActivity

class RecommendationAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    private var context: Context? = null

    class ViewHolder(val binding: ViewholderRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationAdapter.ViewHolder {
        //nhớ khai báo context: Context trước và import thư viện android.content.Context
        context = parent.context
        val binding = ViewholderRecommendationBinding.inflate(LayoutInflater.from(context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationAdapter.ViewHolder, position: Int) {
        holder.binding.txtProductName.text = items[position].productName
        holder.binding.txtPrice.text = "$" + items[position].price.toString()
        holder.binding.txtRating.text = items[position].rating.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.imgProduct)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}