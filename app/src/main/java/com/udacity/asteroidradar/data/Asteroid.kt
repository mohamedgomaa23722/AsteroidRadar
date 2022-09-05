package com.udacity.asteroidradar.data

import android.os.Parcelable
import androidx.lifecycle.Transformations.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
@Entity(tableName = "asteroid_table")
@JsonClass(generateAdapter = true)
data class Asteroid(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "codename")
    val codename: String,
    @ColumnInfo(name = "closeApproachDate")
    val closeApproachDate: String,
    @ColumnInfo(name = "absoluteMagnitude")
    val absoluteMagnitude: Double,
    @ColumnInfo(name = "estimatedDiameter")
    val estimatedDiameter: Double,
    @ColumnInfo(name = "relativeVelocity")
    val relativeVelocity: Double,
    @ColumnInfo(name = "distanceFromEarth")
    val distanceFromEarth: Double,
    @ColumnInfo(name = "isPotentiallyHazardous")
    val isPotentiallyHazardous: Boolean
) : Parcelable

fun List<Asteroid>.asDatabaseModel(): Array<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename= it.codename,
            closeApproachDate= it.closeApproachDate,
            absoluteMagnitude= it.absoluteMagnitude,
            estimatedDiameter= it.estimatedDiameter,
            relativeVelocity= it.relativeVelocity,
            distanceFromEarth= it.distanceFromEarth,
            isPotentiallyHazardous= it.isPotentiallyHazardous
        )
    }.toTypedArray()
}



