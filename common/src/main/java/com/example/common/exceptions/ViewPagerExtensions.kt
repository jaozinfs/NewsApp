package com.example.common.exceptions

import androidx.viewpager2.widget.ViewPager2
import com.example.common.R

fun ViewPager2.transformCaroucel() {
    val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.defaultPagerMargin)
    val offsetPx = resources.getDimensionPixelOffset(R.dimen.defaultPagerOffset)
    setPageTransformer { page, position ->
        val myOffset: Float = position * -(2 * offsetPx + pageMarginPx)
        if (position < -1) {
            page.translationX = -myOffset
        } else if (position <= 1) {
            val scaleFactor =
                Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
            page.translationX = myOffset
            page.scaleY = scaleFactor
            page.alpha = scaleFactor
        } else {
            page.alpha = 0f
            page.translationX = myOffset
        }
    }
}