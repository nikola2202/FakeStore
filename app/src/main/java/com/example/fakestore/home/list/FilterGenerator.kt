package com.example.fakestore.home.list

import com.example.fakestore.model.domain.Filter
import com.example.fakestore.model.domain.Product
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    fun generateFrom(productsList: List<Product>): Set<Filter> {
        return productsList.groupBy {
            it.category
        }.map {mapEntry->
            Filter(value = mapEntry.key, displayText = "${mapEntry.key} (${mapEntry.value.size})")
        }.toSet()
    }

}