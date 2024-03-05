package com.example.fakestore

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.example.fakestore.databinding.EpoxyModelProductItemBinding
import com.example.fakestore.model.domain.Product
import java.text.NumberFormat

data class ProductEpoxyModel(
    val product: Product
): ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()
    override fun EpoxyModelProductItemBinding.bind() {
        productTitleTextView.text = product.title
        productDescriptionTextView.text = product.description
        productCategoryTextView.text = product.category
        productPriceTextView.text = currencyFormatter.format(product.price)

        productImageViewLoadingProgressBar.isVisible = true
        productImageView.load(data = product.image) {
            listener { request, result ->
                productImageViewLoadingProgressBar.isGone = true
            }
        }

    }

}
