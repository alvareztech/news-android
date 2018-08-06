package tech.alvarez.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomappbar.BottomAppBar
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String?) {
    Glide.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this);
}

fun Date.formatTimeDefaults(): String {
    val sdf = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
    return sdf.format(this)
}

fun BottomAppBar.toggleFabAlignment() {
    val current = fabAlignmentMode
    fabAlignmentMode = current.xor(1)
}