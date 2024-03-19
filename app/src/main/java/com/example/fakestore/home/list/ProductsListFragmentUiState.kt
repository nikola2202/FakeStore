package com.example.fakestore.home.list

import com.example.fakestore.model.domain.Filter
import com.example.fakestore.model.ui.UiFilter
import com.example.fakestore.model.ui.UiProduct

sealed interface ProductsListFragmentUiState {
    data class Success(
        val filters: Set<UiFilter>,
        val products: List<UiProduct>
    ): ProductsListFragmentUiState

    object Loading: ProductsListFragmentUiState
}

