package com.example.fakestore.model.domain

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: java.math.BigDecimal,
    val title: String
)
