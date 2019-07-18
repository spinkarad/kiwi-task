package rsp.example.kiwitask.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions.placeholderOf
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


@BindingAdapter("timestamp")
fun TextView.setDateByTimestamp(timestamp: Long) {
    val sdf = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
    Date(timestamp * 1000).let {
        text = sdf.format(it)
    }
}
