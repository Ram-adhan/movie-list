package com.inbedroom.edottest.shared.ui.carousel

import android.content.res.Resources
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import kotlin.math.abs

fun getCarouselDefaultTransformers() = CompositePageTransformer().apply {
    addTransformer(MarginPageTransformer((10 * Resources.getSystem().displayMetrics.density).toInt()))
    addTransformer { page, position ->
        val r = 1 - abs(position)
        val scale = (0.95f + r * 0.05f)
        val dim = (0.6f + r * 0.40f)
        page.scaleY = scale
        page.alpha = dim
    }
}