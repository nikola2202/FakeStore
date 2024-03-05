package com.example.fakestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.fakestore.databinding.ActivityMainBinding
import com.example.fakestore.hilt.service.ProductsService
import com.example.fakestore.model.domain.Product
import com.example.fakestore.model.mapper.ProductMapper
import com.example.fakestore.model.network.NetworkProduct
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productsService: ProductsService

    @Inject
    lateinit var productMapper: ProductMapper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        lifecycleScope.launchWhenStarted {
            val response: Response<List<NetworkProduct>> = productsService.getAllProducts()
            val domainProduct: List<Product> = response.body()!!.map {
                productMapper.buildForm(networkProduct = it)
            } ?: emptyList()
            controller.setData(domainProduct)

            if (domainProduct.isEmpty()) {
                Snackbar.make(binding.root,"Failed to fetch",Snackbar.LENGTH_LONG).show()
            }

        }

    }
    private fun setupListeners() {
        /*binding.cardView.setOnClickListener {
            binding.productDescriptionTextView.apply {
                isVisible = !isVisible
            }
        }

        var isFavorite = false
        binding.favoriteImageView.setOnClickListener {
            val imageRes = if (isFavorite) {
                R.drawable.ic_round_favorite_border_24
            } else {
                R.drawable.ic_round_favorite_24
            }
            binding.favoriteImageView.setIconResource(imageRes)
            isFavorite = !isFavorite*/

        }

}