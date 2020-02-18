package com.perisic.luka.data.model

import com.perisic.luka.data.remote.model.helpers.UserDetails

class SeekBarChoice(
        type: String,
        val title: String,
        val discrete: Boolean,
        val min: Int,
        val max: Int,
        var selected: Int?
    ): Choice<SeekBarChoice>(type) {

        override fun apply(userDetails: UserDetails?): SeekBarChoice {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
}