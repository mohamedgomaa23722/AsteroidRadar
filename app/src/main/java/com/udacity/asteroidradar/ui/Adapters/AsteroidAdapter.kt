package com.udacity.asteroidradar.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.AsteriodItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AsteroidAdapter (private val onClickListener: OnClickListener<Asteroid>) : ListAdapter<Asteroid, AsteroidViewHolder>(DiffCallback) {
    /**
     * This is an override method that create our view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        //we using binding to pass it to AsteroidViewHolder
        val binding: AsteriodItemBinding =
            AsteriodItemBinding.inflate(LayoutInflater.from(parent.context))
        return AsteroidViewHolder(binding)
    }

    /**
     * This is an override method to bind the data on the views
     */
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        //First get item from our List
        val asteroid = getItem(position)
        //Handle onclick item click listener
        holder.itemView.setOnClickListener{
            //when clicked pass the selected asteroid
            onClickListener.clickListener(asteroid)
        }
        //use the bind fun in AsteroidViewHolder to bind data to our view
        holder.bind(asteroid)
    }

    /**
     * Using DiffUtil to compare the old list and the new one for refreshing data
     * and avoid repeating to observe each item for beginning.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            // at this fun we compare the old object with new object by address
            // if old has the same address of new so return true else false
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            // at this fun we only compare with a unique element like id at this case
            // so if the old id is equals to new id return true else false
            return oldItem.id == newItem.id
        }
    }
}