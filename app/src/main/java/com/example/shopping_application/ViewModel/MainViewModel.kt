package com.example.shopping_application.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopping_application.Model.ItemsModel
import com.example.shopping_application.Model.ProductTypeModel
import com.example.shopping_application.Model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel(): ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<List<SliderModel>>()
    private val _productType = MutableLiveData<MutableList<ProductTypeModel>>()
    private val _recommendation = MutableLiveData<MutableList<ItemsModel>>()

    val banners: LiveData<List<SliderModel>> = _banner
    val productTypes: LiveData<MutableList<ProductTypeModel>> = _productType
    val recommendation: LiveData<MutableList<ItemsModel>> = _recommendation

    fun loadBanners() {
        val Ref = firebaseDatabase.getReference("Banner")
        Ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun loadProductType() {
        val Ref = firebaseDatabase.getReference("ProductType")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ProductTypeModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ProductTypeModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _productType.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadRecommendation() {
        val Ref = firebaseDatabase.getReference("Products")
        Ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _recommendation.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}