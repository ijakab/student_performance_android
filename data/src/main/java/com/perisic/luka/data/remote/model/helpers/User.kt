package com.perisic.luka.data.remote.model.helpers

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class User (
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    val role: String,
    @SerializedName("created_at") val createdAt: String,
    val details: UserDetails? = null
) {

    companion object {

        val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem

        }
    }

}