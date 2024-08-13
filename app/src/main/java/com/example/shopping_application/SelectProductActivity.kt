package com.example.shopping_application

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.shopping_application.Adapter.ListProductAdapter
import com.example.shopping_application.Adapter.SizeAdapter
import com.example.shopping_application.Helper.ManagmentCart
import com.example.shopping_application.Model.ItemsModel
import com.example.shopping_application.databinding.ActivitySelectProductBinding

class SelectProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectProductBinding
    private lateinit var item: ItemsModel
    private var numberOder = 1
    private lateinit var managementCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagmentCart(this)



        getBundle()

        // Tải hình ảnh ban đầu
        updateProductImage(0)

        initLists()
    }

        private fun initLists() {
            val sizeList = ArrayList<String>()
            for (size in item.size) {
                sizeList.add(size.toString())
            }

            binding.rvListSize.adapter = SizeAdapter(sizeList)
            binding.rvListSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

//            val listProduct = ArrayList<String>()
//            for (imageUrl in item.picUrl) {
//                listProduct.add(imageUrl)
//            }

            binding.rvListProduct.adapter = ListProductAdapter(item.picUrl, this) // Truyền listener
            binding.rvListProduct.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.txtProductName.text = item.productName
        binding.txtPrice.text = "$" + item.price
        binding.btnAddToCart.setOnClickListener {
            item.numberInCart = numberOder
            managementCart.insertFood(item)
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun updateProductImage(position: Int) {
        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(this)
            .load(item.picUrl[position])
            .apply(requestOptions)
            .into(binding.imgProduct)
    }

    // Xử lý sự kiện khi người dùng chọn hình ảnh khác từ RecyclerView
    fun onItemClick(position: Int) {
        updateProductImage(position)
    }
}