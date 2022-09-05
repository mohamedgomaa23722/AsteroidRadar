package com.udacity.asteroidradar.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.ui.Adapters.AsteroidAdapter
import com.udacity.asteroidradar.ui.Adapters.OnClickListener
import com.udacity.asteroidradar.repository.Filter

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Declare our Binding variable
        binding = FragmentMainBinding.inflate(inflater)
        //pass the lifecycle of fragment to notify its life cycle
        binding.lifecycleOwner = this
        //pass the view model
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        //Declare adapter variable
        binding.asteroidRecycler.adapter = AsteroidAdapter(OnClickListener {
            //Navigate to details fragment
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Handle on item menu selected
        when (item.itemId) {
            R.id.show_week_menu -> viewModel.filterAsteroid(Filter.WEEKLY)
            R.id.show_today_menu -> viewModel.filterAsteroid(Filter.TODAY)
            else -> viewModel.filterAsteroid(Filter.SAVED)
        }
        return true
    }
}
