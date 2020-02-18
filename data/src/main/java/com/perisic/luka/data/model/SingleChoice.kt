package com.perisic.luka.data.model

import com.perisic.luka.data.remote.model.helpers.UserDetails

class SingleChoice(
    val type: String,
    val title: String,
    var checked: Boolean = false
    ) {


    fun toDetails(userDetails: UserDetails) {
        userDetails.apply {
            when (type) {
                "extraCurricularActivities" -> {
                    activities = if(checked) "yes" else "no"
                }
                "wantsToTakeHigherEducation" -> {
                    higher = if(checked) "yes" else "no"
                }
                "internetAccessAtHome" -> {
                    internet = if(checked) "yes" else "no"
                }
                "withARomanticRelationship" -> {
                    romantic = if(checked) "yes" else "no"
                }
            }
        }
    }

}