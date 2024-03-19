package com.example.fakestore.home.list

import com.example.fakestore.model.ui.UiFilter
import com.example.fakestore.model.ui.UiProduct

data class ProductsListFragmentUiState(
    val filters: Set<UiFilter>,
    val products: List<UiProduct>
)
