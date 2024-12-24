package com.fooddelivery.utils

fun separateListWithDots(list: List<String>) = list.joinToString(separator = " • ")

fun minuteToHour(minute: Int): String {
    return when (minute) {

        //we can decide what to do when it is 0, but it is an impossible case so I put it as 1
        0, 1 -> "$minute min"
        in 2..59 -> "$minute mins"
        else -> getHoursAndMinute(minute)
    }
}

private fun getHoursAndMinute(minute: Int): String {
    val hours = (minute / 60)
    val remainedMinutes = minute - hours * 60

    val hourString = when {
        hours == 1 -> "hour"
        hours > 1 -> "hours"
        else -> ""
    }
    return if (remainedMinutes == 0)
        "$hours $hourString"
    else "$hours $hourString ${minuteToHour(remainedMinutes)}"


}