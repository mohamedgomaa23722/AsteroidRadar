package com.udacity.asteroidradar.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.data.Asteroid

/**
 * This is Room Data base class it includes our entities classes and version of database
 */
@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    //create a variable of asteroid dao interface class
    abstract val asteroidDao:AsteroidDao
}
private lateinit var INSTANCE:AsteroidDatabase

/**
 * Create Singleton object of AsteroidDatabase
 */
fun getDataBaseInstance(context: Context):AsteroidDatabase{
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroidDB").build()
        }
    }
    return INSTANCE
}