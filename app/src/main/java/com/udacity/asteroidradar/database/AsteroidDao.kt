package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.data.Asteroid

@Dao
interface AsteroidDao {
    /**
     * Initialize our insert function to insert current asteroid
     * and set if asteroid is duplicated replace it don't add
     * new one.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroid(vararg asteroid: Asteroid)

    /**
     * Get All Asteroid from our database
     */
    @Query("select * from asteroid_table where closeApproachDate between :start_date And :end_date  order by closeApproachDate ASC")
    suspend fun getAsteroids(start_date: String, end_date: String): List<Asteroid>?

    @Query("select * from asteroid_table order by closeApproachDate ASC")
    suspend fun getAllAsteroids(): List<Asteroid>?
}