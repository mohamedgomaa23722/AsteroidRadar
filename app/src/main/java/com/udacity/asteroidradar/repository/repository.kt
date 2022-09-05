package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.api.asteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.getDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

private const val TAG = "repository"

class repository(private val asteroidDatabase: AsteroidDatabase) {

    /**
     * This method help us to get all data from api and save it into our cache (ROOM)
     * and refresh it and return the List from dataBase
     **/
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshAsteroid(): List<Asteroid>? {
        //use IO Dispatcher for that process because it most suitable
        //with collecting data from network and can handle all Database operations
        withContext(Dispatchers.IO) {
            try {
                //try to get the response from our server
                val objects =
                    asteroidApi.apiService.getAsteroids(getDate(), getDate(7), Constants.API_KEY)
                        .await()
                //after getting response then create new Json Object
                val jsonObj = JSONObject(objects)
                //then Insert all data into our Cached database and there is some method called (parseAsteroidsJsonResult())
                //this method which in NetworkUtils help us to convert the Json object into ArrayList to observe its
                //value.
                val arr = parseAsteroidsJsonResult(jsonObj)

                asteroidDatabase.asteroidDao
                    .insertAsteroid(*arr.asDatabaseModel())

                Log.i(TAG, "response: with array size = ${arr.size}")

            } catch (e: Exception) {
                //if the request denied or there is connections problem it will throw the exception message
                Log.i(TAG, "getAsteroid: ${e.message}") }
        }
        //Then return data today data from room database
        return getAsteroid(Filter.TODAY.filter)
    }

    /**
     * This function is suspend as you see and it is for get Asteroid by filter type
     * like if it is Weekly, Today, Saved Filters from Room Data Base
     */
    suspend fun getAsteroid(filterType: String): List<Asteroid>? = when (filterType) {
        Filter.WEEKLY.filter -> asteroidDatabase.asteroidDao.getAsteroids(getDate(), getDate(7))
        Filter.TODAY.filter -> asteroidDatabase.asteroidDao.getAsteroids(getDate(), getDate())
        else -> asteroidDatabase.asteroidDao.getAllAsteroids()
    }
}

enum class Filter(val filter: String) {
    WEEKLY("WEEKLY"),
    TODAY("TODAY"),
    SAVED("saved")
}
