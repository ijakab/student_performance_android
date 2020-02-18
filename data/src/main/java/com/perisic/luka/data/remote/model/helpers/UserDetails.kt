package com.perisic.luka.data.remote.model.helpers

import com.google.gson.annotations.SerializedName
import com.perisic.luka.data.model.Choice
import com.perisic.luka.data.model.SeekBarChoice
import com.perisic.luka.data.model.SingleChoice
import com.perisic.luka.data.model.SpinnerChoice

open class UserDetails(
    var age: String?,
    var sex: String? = "M",
    var address: String?,
    @SerializedName("famsize") var familySize: String?,
    @SerializedName("Pstatus") var parentStatus: String?,
    @SerializedName("Medu") var motherEducation: String?,
    @SerializedName("Fedu") var fatherEducation: String?,
    @SerializedName("Mjob") var mothersJob: String?,
    @SerializedName("Fjob") var fatherJob: String?,
    @SerializedName("traveltime") var travelTime: String?,
    @SerializedName("studytime") var studyTime: String?,
    var activities: String?,
    var higher: String?,
    var internet: String?,
    var romantic: String?,
    @SerializedName("freetime") var freeTime: String?,
    @SerializedName("goout") var goOut: String?,
    @SerializedName("Dalc") var dailyAlcohol: String?,
    @SerializedName("Walc") var weeklyAlcohol: String?,
    @SerializedName("absences") var absences: String?,
    val G3: String? = null
)


class UserDetailsParsed(userDetails: UserDetails) : UserDetails(
    userDetails.age,
    userDetails.sex,
    userDetails.address,
    userDetails.familySize,
    userDetails.parentStatus,
    userDetails.motherEducation,
    userDetails.fatherEducation,
    userDetails.fatherJob,
    userDetails.travelTime,
    userDetails.studyTime,
    userDetails.activities,
    userDetails.higher,
    userDetails.internet,
    userDetails.romantic,
    userDetails.freeTime,
    userDetails.goOut,
    userDetails.dailyAlcohol,
    userDetails.weeklyAlcohol,
    userDetails.absences,
    userDetails.G3
) {

    var spinnerChoices: ArrayList<SpinnerChoice>? = null
    var seekBarChoices: ArrayList<SeekBarChoice>? = null
    var singleChoices: ArrayList<SingleChoice>? = null

    init {
        spinnerChoices = createSpinnerDataList(userDetails)
        seekBarChoices = createSeekBarChoiceDataList(userDetails)
        singleChoices = createSingleChoiceDataList(userDetails)
    }

    fun applySeekBarChoices(choices: ArrayList<SeekBarChoice>) {
        choices.forEach {
            when (it.type) {
                "age" -> age = it.selected?.toString()
                "freeTimeAfterSchool" -> freeTime = it.selected?.toString()
                "goingOutWithFriends" -> goOut = it.selected?.toString()
                "workdayAlcoholConsumption" -> dailyAlcohol = it.selected?.toString()
                "weekendAlcoholConsumption" -> weeklyAlcohol = it.selected?.toString()
                "numberOfSchoolAbsences" -> absences = it.selected?.toString()
            }
        }
    }

    fun applySpinnerChoices(choices: ArrayList<SpinnerChoice>) {
        choices.forEach {
            it.toDetails(this)
        }
    }

    fun applySingleChoices(choices: ArrayList<SingleChoice>) {
        choices.forEach {
            it.toDetails(this)
        }
    }

    private fun createSingleChoiceDataList(userDetails: UserDetails): java.util.ArrayList<SingleChoice>? {
        return arrayListOf(
            SingleChoice(
                type = "extraCurricularActivities",
                title = "Extra-curricular activities",
                checked = userDetails.activities?.equals("yes") == true
            ),
            SingleChoice(
                type = "wantsToTakeHigherEducation",
                title = "Wants to take higher education",
                checked = userDetails.higher?.equals("yes") == true
            ),
            SingleChoice(
                type = "internetAccessAtHome",
                title = "Internet access at home",
                checked = userDetails.internet?.equals("yes") == true
            ),
            SingleChoice(
                type = "withARomanticRelationship",
                title = "With a romantic relationship",
                checked = userDetails.romantic?.equals("yes") == true
            )
        )
    }

    private fun createSpinnerDataList(userDetails: UserDetails?): ArrayList<SpinnerChoice> {
        val list = ArrayList<SpinnerChoice>()
            .apply {
                addAll(
                    arrayListOf(
                        SpinnerChoice(
                            type = "sex",
                            hint = "Sex",
                            choices = arrayListOf("Male", "Female")
                        ),
                        SpinnerChoice(
                            type = "address",
                            hint = "Address",
                            choices = arrayListOf(
                                "Urban",
                                "Rural"
                            )
                        ),
                        SpinnerChoice(
                            type = "familySize",
                            hint = "Family size",
                            choices = arrayListOf("Less than 3", "Greater than 3")
                        ),
                        SpinnerChoice(
                            type = "parentStatus",
                            choices = arrayListOf("Together", "Apart"),
                            hint = "Parent status"
                        ),//mother's education (numeric: 0 - none, 1 - primary education (4th grade), 2 â€“ 5th to 9th grade, 3 â€“ secondary education or 4 â€“ higher education)
                        SpinnerChoice(
                            type = "mothersEducation",
                            choices = arrayListOf(
                                "none",
                                "primary education (4th grade)",
                                "5th to 9th grade",
                                "secondary education",
                                "higher education"),
                            hint = "Mother's education"
                        ),
                        SpinnerChoice(
                            type = "fathersEducation",
                            choices = arrayListOf(
                                "none",
                                "primary education (4th grade)",
                                "5th to 9th grade",
                                "secondary education",
                                "higher education"),
                            hint = "Fathers education"
                        ),
                        SpinnerChoice(
                            type = "mothersJob",
                            choices = arrayListOf("teacher", "health", "services", "at home", "other"),
                            hint = "Mother's job"
                        ),
                        SpinnerChoice(
                            type = "fathersJob",
                            choices = arrayListOf("teacher", "health", "services", "at home", "other"),
                            hint = "Father's job"
                        ),
                        SpinnerChoice(
                            type = "homeToSchoolTravelTime",//(numeric: 1 - <15 min., 2 - 15 to 30 min., 3 - 30 min. to 1 hour, or 4 - >1 hour)
                            choices = arrayListOf(
                                "<15 min.",
                                "15 to 30 min.",
                                "30 min. to 1 hour",
                                ">1 hour"
                            ),
                            hint = "Home to school travel time"
                        ),
                        SpinnerChoice(//(numeric: 1 - <2 hours, 2 - 2 to 5 hours, 3 - 5 to 10 hours, or 4 - >10 hours)
                            type = "weeklyStudyTime",
                            hint = "Weekly study time",
                            choices = arrayListOf("<2 hours", "2 to 5 hours", "5 to 10 hours", ">10 hours")
                        )
                    ).apply(userDetails)
                )
            }
        return list
    }

    private fun createSeekBarChoiceDataList(userDetails: UserDetails): ArrayList<SeekBarChoice> {
        return arrayListOf(
            SeekBarChoice(
                type = "age",
                title = "Age",
                min = 15,
                max = 22,
                discrete = true,
                selected = if (userDetails.age?.isEmpty() != true) userDetails.age?.toInt() else null
            ),
            SeekBarChoice(
                type = "freeTimeAfterSchool",
                title = "Free time after school",
                min = 1,
                max = 5,
                discrete = true,
                selected = if (userDetails.freeTime?.isEmpty() != true) userDetails.freeTime?.toInt() else null
            ),
            SeekBarChoice(
                type = "goingOutWithFriends",
                title = "Going out with friends",
                min = 1,
                max = 5,
                discrete = true,
                selected = if (userDetails.goOut?.isEmpty() != true) userDetails.goOut?.toInt() else null
            ),
            SeekBarChoice(
                type = "workdayAlcoholConsumption",
                title = "Workday alcohol consumption",
                min = 1,
                max = 5,
                discrete = true,
                selected = if (userDetails.dailyAlcohol?.isEmpty() != true) userDetails.dailyAlcohol?.toInt() else null
            ),
            SeekBarChoice(
                type = "weekendAlcoholConsumption",
                title = "Weekend alcohol consumption",
                min = 1,
                max = 5,
                discrete = true,
                selected = if (userDetails.weeklyAlcohol?.isEmpty() != true) userDetails.weeklyAlcohol?.toInt() else null
            ),
            SeekBarChoice(
                type = "numberOfSchoolAbsences",
                title = "Number of school absences",
                min = 0,
                max = 100,
                discrete = false,
                selected = if (userDetails.absences?.isEmpty() != true) userDetails.absences?.toInt() else null
            )
        )
    }

    private fun <V, T : Choice<V>> ArrayList<T>.apply(userDetails: UserDetails?): List<V> {
        return map {
            it.apply(userDetails)
        }
    }

}