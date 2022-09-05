package com.udacity.asteroidradar.ui.Adapters

import android.view.View
import com.udacity.asteroidradar.data.Asteroid

class OnClickListener<T>(val clickListener: (item: T) -> Unit){
    fun onClick(item: T) = clickListener(item)
}