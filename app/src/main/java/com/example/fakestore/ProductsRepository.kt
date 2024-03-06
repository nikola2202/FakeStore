package com.example.fakestore

import com.example.fakestore.hilt.service.ProductsService
import com.example.fakestore.model.domain.Product
import com.example.fakestore.model.mapper.ProductMapper
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productMapper: ProductMapper
) {

    suspend fun fetchAllProducts() : List<Product> {
        //todo better error handling
        return productsService.getAllProducts().body()?.let { networkProducts ->
            networkProducts.map { productMapper.buildForm(it) }
        } ?: emptyList()
    }

}