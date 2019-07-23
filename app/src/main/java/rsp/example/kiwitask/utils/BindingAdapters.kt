package rsp.example.kiwitask.utils

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.util.toAndroidPair
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.placeholderOf
import com.google.android.material.snackbar.Snackbar
import rsp.example.kiwitask.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Radek Špinka on 17.07.2019.
 */

@BindingAdapter("imageUrl", "corners", "placeholder", requireAll = false)
fun ImageView.setImageWithRoundedCorners(url: String?, cornerInDp: Int?, placeholder: Drawable?) {
    url?.let {
        val radius = (cornerInDp ?: 0) * context.resources.displayMetrics.density
        Glide.with(context).asDrawable().run {
            if (placeholder != null) apply(placeholderOf(placeholder))
            load(it)
            transform(CenterCrop(), RoundedCorners(radius.toInt()))
            diskCacheStrategy(DiskCacheStrategy.DATA)
            transition(DrawableTransitionOptions().crossFade())
            into(this@setImageWithRoundedCorners)
        }
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("timestamp", "suffixText")
fun TextView.setDateByTimestamp(timestamp: Long, suffix: String) {
    val sdf = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    Date(timestamp * 1000).let {
        text = "${sdf.format(it)} $suffix"
    }
}

@BindingAdapter("timestampStart", "timestampEnd", requireAll = true)
fun TextView.setDateRangeByTimestamps(timestampStart: Long, timestampEnd: Long) {
    val sdf = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    val start = Date(timestampStart * 1000).let(sdf::format)
    val end = Date(timestampEnd * 1000).let(sdf::format)

    text = resources.getString(R.string.date_range, start, end)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("legTimestamp")
fun TextView.setLegTime(timestamp: Long) {
    val sdf = SimpleDateFormat("MMM dd hh:mm", Locale.getDefault())
    Date(timestamp * 1000).let {
        text = sdf.format(it)
    }
}

@BindingAdapter("forward", "back", requireAll = true)
fun ImageView.setDirectionIcon(forward: Boolean, back: Boolean) {
    if (forward.not() && back.not()) {
        visibility = View.GONE
        return
    } else visibility = View.VISIBLE

    val src = if (forward) {
        R.drawable.ic_arrow_forward
    } else {
        R.drawable.ic_arrow_back
    }
    setImageResource(src)
}

fun View.showSnack(@StringRes stringResId: Int, duration: Int = Snackbar.LENGTH_LONG): Snackbar {
    val snack = Snackbar.make(this, stringResId, duration)
    snack.show()
    return snack
}
