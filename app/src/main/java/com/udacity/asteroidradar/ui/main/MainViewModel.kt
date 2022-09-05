package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.asteroidApi
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.getDataBaseInstance
import com.udacity.asteroidradar.repository.Filter
import com.udacity.asteroidradar.repository.repository
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val asteroidDatabase: AsteroidDatabase = getDataBaseInstance(application)
    private val repository: repository = repository(asteroidDatabase)

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids

    init {
        refreshAstroid()
        PictureOfDay()
    }

    /**
     * Refresh asteroid data and initialize the picture of day
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun refreshAstroid() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val databaseResponse:List<Asteroid> =  repository.refreshAsteroid()!!
                withContext(Dispatchers.Main){
                    _asteroids.value = databaseResponse
                    _statue.value = STATUE.SUCCESS
                }
            } catch (e: Exception) {
                _statue.value = STATUE.ERROR
            }
        }

        filterAsteroid(Filter.TODAY)
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    private val _statue = MutableLiveData(STATUE.LOADING)

    val statue: LiveData<STATUE>
        get() = _statue

    /**
     * This function help us to get the picture of day data
     */
    private fun PictureOfDay() {
        // launch this in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // try to set the picture of day data into mutableLiveData
                val PictureOfDay = asteroidApi.apiService.getPictureOfDay(Constants.API_KEY).await()
                withContext(Dispatchers.Main) {
                    _pictureOfDay.value = PictureOfDay
                }

            } catch (e: Exception) {
                //throw an Exception if there is any problem like network problem or other
                Log.i("ViewModel", "PictureOfDay: ${e.message} ")
            }
        }
    }

    /**
     * This Method to get Asteroid Data from room database
     */
    fun filterAsteroid(filter: Filter) {
        //launch this on view model scope
        viewModelScope.launch {
            //set asteroid value
            _asteroids.value = repository.getAsteroid(filter.filter)
            Log.d("TAG", "filterAsteroid: " + _asteroids.value?.size)
            if (_asteroids.value?.size != 0)
                _statue.value = STATUE.SUCCESS
        }
    }
}

enum class STATUE(val statue: String) { ERROR("ERROR"), SUCCESS("SUCCESS"), LOADING("LOADING") }