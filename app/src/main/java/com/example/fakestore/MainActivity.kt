package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productsService: ProductService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenCreated {
            val response = productsService.getAllProducts()
            Log.i("DATA",response.body()!!.toString())
        }

    }

    interface ProductService {

        @GET("products")
        suspend fun getAllProducts(): Response<List<Any>>

    }

}