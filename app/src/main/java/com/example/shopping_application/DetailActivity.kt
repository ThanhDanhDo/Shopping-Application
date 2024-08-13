package com.example.shopping_application

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_application.Adapter.ListProductAdapter
import com.example.shopping_application.Adapter.SizeAdapter
import com.example.shopping_application.Adapter.SliderAdapter
import com.example.shopping_application.Helper.ManagmentCart
import com.example.shopping_application.Model.ItemsModel
import com.example.shopping_application.Model.SliderModel
import com.example.shopping_application.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOder = 1
    private lateinit var managementCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        managementCart = ManagmentCart(this)

        getBundle()
        banners()
//        rvListSize va rvListProductType(color)
//        initLists()
    }

//    private fun initLists() {

//        val sizeList = ArrayList<String>()
//        for (size in item.size) {
//            sizeList.add(size.toString())
//        }
//
//        binding.rvListSize.adapter = SizeAdapter(sizeList)
//        binding.rvListSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//        val listProduct = ArrayList<String>()
//        for (imageUrl in item.picUrl) {
//            listProduct.add(imageUrl)
//        }
//
//        binding.rvListProduct.adapter = ListProductAdapter(listProduct)
//        binding.rvListProduct.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.vp2Slider.adapter = SliderAdapter(sliderItems, binding.vp2Slider)
        binding.vp2Slider.clipToPadding = true
        binding.vp2Slider.clipChildren = true
        binding.vp2Slider.offscreenPageLimit = 1

        if(sliderItems.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.vp2Slider)
        }
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.txtProductName.text = item.productName
        binding.txtDescription.text = item.description
        binding.txtPrice.text = "$" + item.price
        binding.txtRating.text = "${item.rating} Ratting"
        binding.btnAddToCart.setOnClickListener {
//            item.numberInCart = numberOder
//            managementCart.insertFood(item)
            //Nếu product không có size và color chỉ có 1 thì add to cart trực tiếp
            if (item.size.isEmpty() && item.picUrl.size == 1) {
                item.numberInCart = numberOder
                managementCart.insertFood(item)
            }
            //product có size và có hơn 1 color
            else {
                val intent = Intent(this@DetailActivity, SelectProductActivity::class.java)
                intent.putExtra("object", item)
                startActivity(intent)
            }
        }
        binding.btnBack.setOnClickListener { finish() }
        binding.btnCart.setOnClickListener {

        }

    }
}