package com.udacity.asteroidradar.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.utils.Constants.API_QUERY_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

/**
 * This function generate Date Depend on the input days like if
 * we need to get the previous few days so we will base how much
 * days we need to, EX: current date = "2022-09-04" and we need to generate "2022-08-30"
 * so call the fun getDate(Days = 5)
 */
@RequiresApi(Build.VERSION_CODES.N)
fun getDate(Days: Int): String {
    //create an instance of calendar object
    val calendar = Calendar.getInstance()
    //then add minus of days to get the previous days
    calendar.add(Calendar.DAY_OF_YEAR, Days)
    //then get current time in millis from generated calander
    val currentTime = calendar.timeInMillis
    //then generate a new format following that format "YYYY-MM-DD"
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    //return string date which following our format by using data format we have generated before
    Log.i("Dates", "Date: ${dateFormat.format(currentTime)}")
    return dateFormat.format(currentTime)
}

/**
 * This function is same copy of above function but at here we need to get
 * current date and it doesn't take any argument
 */
fun getDate(): String {
    //get date with out any changing in days
    return getDate(0)
}