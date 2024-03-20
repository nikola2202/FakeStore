package com.example.fakestore.home.list

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.fakestore.model.domain.Filter
import kotlinx.coroutines.launch
import java.util.UUID

class UiProductEpoxyController(
    private val viewModel: ProductsListViewModel
): TypedEpoxyController<ProductsListFragmentUiState>() {
    override fun buildModels(data: ProductsListFragmentUiState?) {

        when (data) {
            is ProductsListFragmentUiState.Success -> {
                val uiFilterModels = data.filters.map { uiFilter ->
                    UiProductFilterEpoxyModel(uiFilter = uiFilter, onFilterSelected = ::onFilterSelected)
                        .id(uiFilter.filter.value)
                }
                CarouselModel_().models(uiFilterModels).id("filters").addTo(this)

                data.products.forEach { uiProduct ->
                    UiProductEpoxyModel(
                        uiProduct = uiProduct,
                        onFavoriteIconClicked = ::onFavoriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked,
                        onAddToCartClicked = ::onAddToCartClicked
                    ).id(uiProduct.product.id).addTo(this)
                }
            }
            is ProductsListFragmentUiState.Loading -> {
                repeat(7) {
                    val epoxyId = UUID.randomUUID().toString()
                    UiProductEpoxyModel(
                        uiProduct = null,
                        onFavoriteIconClicked = ::onFavoriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked,
                        onAddToCartClicked = ::onAddToCartClicked
                    ).id(epoxyId).addTo(this)
                }
            }

            else -> {
                throw RuntimeException("Unhandled branch! $data")
            }
        }
    }

    private fun onFavoriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentFavoriteIds = currentState.favoriteProductIds
                val newFavoriteIds = if (currentFavoriteIds.contains(selectedProductId)) {
                    currentFavoriteIds.filter { it != selectedProductId }.toSet()
                } else {
                    currentFavoriteIds + setOf(selectedProductId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
            }
        }
    }
    private fun onUiProductClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentExpandedIds = currentState.expandedProductIds
                val newExpandedIds = if (currentExpandedIds.contains(productId)) {
                    currentExpandedIds.filter { it != productId }.toSet()
                } else {
                    currentExpandedIds + setOf(productId)
                }
                return@update currentState.copy(expandedProductIds = newExpandedIds)
            }
        }
    }

    private fun onFilterSelected(filter: Filter) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentlySelectedState = currentState.productFilterInfo.selectedFilter
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = if (currentlySelectedState != filter) {
                            filter
                        } else {
                            null
                        }
                    )
                )
            }
        }
    }

    private fun onAddToCartClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState->
                val currentProductIdsInCart = currentState.inCartProductIds
                val newProductIdsInCart = if(currentProductIdsInCart.contains(productId)) {
                    currentProductIdsInCart.filter { it != productId }.toSet()
                } else {
                    currentProductIdsInCart + setOf(productId)
                }
                return@update currentState.copy(inCartProductIds = newProductIdsInCart)
            }
        }
    }

}

