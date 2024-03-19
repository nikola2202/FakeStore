package com.example.fakestore.home.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.hilt.repository.ProductsRepository
import com.example.fakestore.model.domain.Filter
import com.example.fakestore.model.domain.Product
import com.example.fakestore.redux.ApplicationState
import com.example.fakestore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val productsRepository: ProductsRepository,
    private val filterGenerator: FilterGenerator

): ViewModel() {
    fun refreshProducts() = viewModelScope.launch {
        val products: List<Product> = productsRepository.fetchAllProducts()
        val filters: Set<Filter> = filterGenerator.generateFrom(products)
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products,
                productFilterInfo = ApplicationState.ProductFilterInfo(
                    filters = filters,
                    selectedFilter = null
                )
            )
        }
    }
}