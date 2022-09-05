package com.udacity.asteroidradar.api


import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.data.PictureOfDay
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface apiService {
    /**
     * This end point help us to get the asteroid between two dates start date
     * and end date auth with api key
     */
    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") Apikey: String
    ): Deferred<String>

    /**
     * This end point help us to get the picture of Day
     */
    @GET("planetary/apod")
    fun getPictureOfDay(@Query("api_key") Apikey: String): Deferred<PictureOfDay>
}