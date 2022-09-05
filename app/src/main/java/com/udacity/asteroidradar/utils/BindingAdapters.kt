package com.udacity.asteroidradar

import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.ui.Adapters.AsteroidAdapter
import com.udacity.asteroidradar.ui.main.STATUE

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.resources.getString(R.string.potentially_hazardous_asteroid_image)

    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.resources.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.resources.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.resources.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
    textView.contentDescription = textView.text
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
    textView.contentDescription = textView.text
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
    textView.contentDescription = textView.text
}

/**
 * This Method help us to observe the picture of day and check its media type
 * because at some cases it may be video so we need to avoid video case to
 * be safe, and set the content description for that image.
 */
@BindingAdapter("picOfDay")
fun bindImageViewToDisplayPictureOfDay(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    if (pictureOfDay?.mediaType == "image") {
        // then observe image
        Picasso.with(imageView.context)
            .load(pictureOfDay.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .into(imageView)
        imageView.contentDescription = String.format(imageView.resources.getString(R.string.nasa_picture_of_day_content_description_format),pictureOfDay.title)
    }else{
        imageView.contentDescription = imageView.resources.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    }
}


@BindingAdapter("spinnerResult")
fun bindspinnner(progressBar: ProgressBar, statue: STATUE) {
    when (statue) {
        STATUE.SUCCESS -> {progressBar.visibility = View.GONE }
        STATUE.LOADING -> {progressBar.visibility = View.VISIBLE }
        else -> {progressBar.visibility = View.VISIBLE }
    }
    progressBar.contentDescription ="Loading data"
}


@BindingAdapter("listData")
fun bindingRecyclerView(recyclerView: RecyclerView, data:List<Asteroid>?){
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(data)
}

@BindingAdapter("textAndContentDescription")
fun bindTextAndContentDescription(textView: TextView, value:String){
    textView.text = value
    textView.contentDescription = value
}
