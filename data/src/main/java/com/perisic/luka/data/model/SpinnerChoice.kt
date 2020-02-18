package com.perisic.luka.data.model

import com.perisic.luka.data.remote.model.helpers.UserDetails

class SpinnerChoice(
    type: String,
    val hint: String = type,
    val choices: List<String>,
    var selected: Int? = null
) : Choice<SpinnerChoice>(type) {

    override fun apply(userDetails: UserDetails?): SpinnerChoice {
        userDetails?.apply {
            selected = when (type) {
                "sex" -> {
                    sex?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "address" -> {
                    address?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "familySize" -> {
                    familySize?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "parentStatus" -> {
                    parentStatus?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "mothersEducation" -> {
                    motherEducation?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "fathersEducation" -> {
                    fatherEducation?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "mothersJob" -> {
                    mothersJob?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "fathersJob" -> {
                    fatherJob?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "homeToSchoolTravelTime" -> {
                    travelTime?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                "weeklyStudyTime" -> {
                    studyTime?.let {
                        typeMap[type]?.indexOf(it)
                    }
                }
                else -> null
            }
        }
        return this
    }

    fun toDetails(userDetails: UserDetails) {
        userDetails.apply {
            when (type) {
                "sex" -> {
                    sex = selected?.let { typeMap[type]?.get(it) }

                }
                "address" -> {
                    address = if( selected != -1) selected?.let { typeMap[type]?.get(it) } else null
                }
                "familySize" -> {
                    familySize = selected?.let { typeMap[type]?.get(it) }
                }
                "parentStatus" -> {
                    parentStatus = selected?.let { typeMap[type]?.get(it) }
                }
                "mothersEducation" -> {
                    motherEducation = selected?.let { typeMap[type]?.get(it) }
                }
                "fathersEducation" -> {
                    fatherEducation = selected?.let { typeMap[type]?.get(it) }
                }
                "mothersJob" -> {
                    mothersJob = selected?.let { typeMap[type]?.get(it) }
                }
                "fathersJob" -> {
                    fatherJob = selected?.let { typeMap[type]?.get(it) }
                }
                "homeToSchoolTravelTime" -> {
                    travelTime = selected?.let { typeMap[type]?.get(it) }
                }
                "weeklyStudyTime" -> {
                    studyTime = selected?.let { typeMap[type]?.get(it) }
                }
            }
        }
    }

    companion object {

        val typeMap = mutableMapOf<String, Array<String>>()

        init {
            typeMap.apply {
                put(
                    "sex",
                    arrayOf("M", "F")
                )
                put(
                    "address",
                    arrayOf("U", "R")
                )
                put(
                    "familySize",
                    arrayOf("LE3", "GT3")
                )
                put(
                    "parentStatus",
                    arrayOf("T", "A")
                )
                put(
                    "mothersEducation",
                    arrayOf("0", "1", "2", "3", "4")
                )
                put(
                    "fathersEducation",
                    arrayOf("0", "1", "2", "3", "4")
                )
                put(
                    "mothersJob",
                    arrayOf("teacher", "health", "services", "at_home", "other")
                )
                put(
                    "fathersJob",
                    arrayOf("teacher", "health", "services", "at_home", "other")
                )
                put(
                    "homeToSchoolTravelTime",
                    arrayOf("1", "2", "3", "4")
                )
                put(
                    "weeklyStudyTime",
                    arrayOf("1", "2", "3", "4")
                )
            }
        }

    }/*# Attributes for both student-mat.csv (Math course) and student-por.csv (Portuguese language course) datasets:
?1 school - student's school (binary: 'GP' - Gabriel Pereira or 'MS' - Mousinho da Silveira)
-2 sex - student's sex (binary: 'F' - female or 'M' - male)
-3 age - student's age (numeric: from 15 to 22)
-4 address - student's home address type (binary: 'U' - urban or 'R' - rural)
-5 famsize - family size (binary: 'LE3' - less or equal to 3 or 'GT3' - greater than 3)
-6 Pstatus - parent's cohabitation status (binary: 'T' - living together or 'A' - apart)
-7 Medu - mother's education (numeric: 0 - none, 1 - primary education (4th grade), 2 â€“ 5th to 9th grade, 3 â€“ secondary education or 4 â€“ higher education)
-8 Fedu - father's education (numeric: 0 - none, 1 - primary education (4th grade), 2 â€“ 5th to 9th grade, 3 â€“ secondary education or 4 â€“ higher education)
-9 Mjob - mother's job (nominal: 'teacher', 'health' care related, civil 'services' (e.g. administrative or police), 'at_home' or 'other')
-10 Fjob - father's job (nominal: 'teacher', 'health' care related, civil 'services' (e.g. administrative or police), 'at_home' or 'other')
11 reason - reason to choose this school (nominal: close to 'home', school 'reputation', 'course' preference or 'other')
12 guardian - student's guardian (nominal: 'mother', 'father' or 'other')
13 traveltime - home to school travel time (numeric: 1 - <15 min., 2 - 15 to 30 min., 3 - 30 min. to 1 hour, or 4 - >1 hour)
14 studytime - weekly study time (numeric: 1 - <2 hours, 2 - 2 to 5 hours, 3 - 5 to 10 hours, or 4 - >10 hours)
15 failures - number of past class failures (numeric: n if 1<=n<3, else 4)
16 schoolsup - extra educational support (binary: yes or no)
17 famsup - family educational support (binary: yes or no)
18 paid - extra paid classes within the course subject (Math or Portuguese) (binary: yes or no)
19 activities - extra-curricular activities (binary: yes or no)
20 nursery - attended nursery school (binary: yes or no)
21 higher - wants to take higher education (binary: yes or no)
22 internet - Internet access at home (binary: yes or no)
23 romantic - with a romantic relationship (binary: yes or no)
24 famrel - quality of family relationships (numeric: from 1 - very bad to 5 - excellent)
25 freetime - free time after school (numeric: from 1 - very low to 5 - very high)
26 goout - going out with friends (numeric: from 1 - very low to 5 - very high)
27 Dalc - workday alcohol consumption (numeric: from 1 - very low to 5 - very high)
28 Walc - weekend alcohol consumption (numeric: from 1 - very low to 5 - very high)
29 health - current health status (numeric: from 1 - very bad to 5 - very good)
30 absences - number of school absences (numeric: from 0 to 93)
*/
}