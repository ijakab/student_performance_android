package com.perisic.luka.studentperformance.adapters

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.perisic.luka.studentperformance.R

@BindingAdapter("userAvatarInitials")
fun loadProfileImage(view: ImageView, displayName: String?) {

    displayName?.let {

        val initials = getUserDisplayNameInitials(displayName)
        val userTextPlaceholder: TextDrawable = TextDrawable.builder()
            .buildRound(
                initials,
                ContextCompat.getColor(view.context, R.color.colorPrimary)
            )
        Glide.with(view.context)
            .load("")
            .apply {
                error(userTextPlaceholder)
                placeholder(userTextPlaceholder)
                into(view)
            }
    }
}

fun getUserDisplayNameInitials(displayName: String?) =
    if (displayName != null)
        "\\b(?=\\w)\\S"
            .toRegex().findAll(displayName)
            .toList()
            .joinToString("") { it.value }
    else ""