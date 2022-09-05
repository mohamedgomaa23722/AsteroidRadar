package com.udacity.asteroidradar.ui.Adapters

import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.AsteriodItemBinding

/**
 * This is our ViewHolderClass which extends from RecyclerView.ViewHolder and
 * take binding as a pass value and here we will define its views and set them
 * by below function.
 */
class AsteroidViewHolder(private val binding: AsteriodItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    /**
     * This function is for binding the data into our view
     * is based by onBindViewHolder override fun in our adapter
     */
    fun bind(asteroid: Asteroid) {
        //set passed asteroid to asteroid variable
        binding.asteroid = asteroid
        //then execute Bindings
        binding.executePendingBindings()
    }
}