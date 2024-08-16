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
    //2 bien selected... để lưu lựa chọn imgUrl và Size trong 2 recyclerView
    private var selectedImageUrl: String? = null
    private var selectedSize: String? = null

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

            val sizeAdapter = SizeAdapter(sizeList) { size ->
                selectedSize = size
            }
            binding.rvListSize.adapter = sizeAdapter
            binding.rvListSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

//            binding.rvListSize.adapter = SizeAdapter(sizeList)
//            binding.rvListSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)



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
            // Lưu ảnh đã chọn và kích thước đã chọn vào item
            selectedImageUrl?.let { item.selectedImageUrl = it }
            selectedSize?.let { item.selectedSize = it }

            managementCart.insertFood(item)
            finish()
        }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun updateProductImage(position: Int) {
        Glide.with(this)
            .load(item.picUrl[position])
            .into(binding.imgProduct)
    }

    // Xử lý sự kiện khi người dùng chọn hình ảnh khác từ RecyclerView
    fun onItemClick(position: Int) {
        selectedImageUrl = item.picUrl[position]  // Lưu URL ảnh được chọn
        updateProductImage(position)
    }
}