package com.example.shopping_application

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.shopping_application.Adapter.ProductTypeAdapter
import com.example.shopping_application.Adapter.RecommendationAdapter
import com.example.shopping_application.Adapter.SliderAdapter
import com.example.shopping_application.Model.SliderModel
import com.example.shopping_application.ViewModel.MainViewModel
import com.example.shopping_application.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()

        initProductType()

        initRecommendation()

        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.btnCart.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun initBanner() {
        //pgbBanner hiển thị lúc đầu, sau khi load data xong ẩn đi
        binding.pgbBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this@MainActivity, Observer { items ->
            banners(items)
            binding.pgbBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images: List<SliderModel>) {
        binding.vpSlider.adapter = SliderAdapter(images, binding.vpSlider)
        binding.vpSlider.clipToPadding = false
        binding.vpSlider.clipChildren = false
        binding.vpSlider.offscreenPageLimit = 3
        binding.vpSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.vpSlider.setPageTransformer(compositePageTransformer)
        if (images.size > 1) {
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.vpSlider)
        }
    }

    private fun initProductType() {
        binding.pgbProductType.visibility = View.VISIBLE
        viewModel.productTypes.observe(this, Observer {
            binding.rvProductType.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.rvProductType.adapter = ProductTypeAdapter(it)
            binding.pgbProductType.visibility = View.GONE
        })
        viewModel.loadProductType()
    }

    private fun initRecommendation() {
        binding.pgbRecommendation.visibility = View.VISIBLE
        viewModel.recommendation.observe(this, Observer {
            binding.rvRecommendation.layoutManager =
                GridLayoutManager(this@MainActivity, 2)
            binding.rvRecommendation.adapter = RecommendationAdapter(it)
            binding.pgbRecommendation.visibility = View.GONE
        })
        viewModel.loadRecommendation()
    }
}