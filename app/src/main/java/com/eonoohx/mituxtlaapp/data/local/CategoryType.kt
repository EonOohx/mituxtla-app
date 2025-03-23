package com.eonoohx.mituxtlaapp.data.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.eonoohx.mituxtlaapp.R

enum class CategoryType(@StringRes val title: Int, @DrawableRes val image: Int) {
    COFFEE_SHOP(R.string.category_coffee_shop, R.drawable.img_coffee_shop),
    RESTAURANT(R.string.category_restaurant, R.drawable.img_restaurant),
    FAMILY_PLACE(R.string.category_family_place, R.drawable.img_family_place),
    PARK(R.string.category_park, R.drawable.img_park),
    SHOPPING_CENTER(R.string.category_shopping_center, R.drawable.img_shopping_center)
}