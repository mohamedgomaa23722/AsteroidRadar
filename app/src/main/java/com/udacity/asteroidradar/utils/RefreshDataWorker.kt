package com.udacity.asteroidradar.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDataBaseInstance
import com.udacity.asteroidradar.repository.repository
import retrofit2.HttpException

class RefreshDataWorker (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }


    override suspend fun doWork(): Result {
        val database = getDataBaseInstance(applicationContext)
        val repository = repository(database)
        return try {
            repository.refreshAsteroid()
            Log.i("TAG", "delayedInit: start worker")
            Result.success()
        } catch (e: HttpException) {
            Log.i("TAG", "delayedInit: error worker  : ${e.message}")
            Result.retry()
        }
    }
}