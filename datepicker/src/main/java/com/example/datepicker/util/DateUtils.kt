package com.example.datepicker.util

import java.time.LocalDate

object DateUtils {

    fun format(localDate: LocalDate, format: DateFormat): String {
        val str = StringBuilder()
        when(format) {
            DateFormat.DD_MM_YYYY -> {
                str.append(localDate.dayOfMonth).append("/")
                    .append(localDate.month.value).append("/")
                    .append(localDate.year)
            }
            DateFormat.DD_MMM_YYYY -> {
                str.append(localDate.dayOfMonth).append(" ")
                    .append(localDate.month.name).append(" ")
                    .append(localDate.year)
            }
            DateFormat.DD_MMMM -> {
                str.append(localDate.dayOfMonth).append(" ")
                    .append(localDate.month.name.subSequence(0, 3))
            }
            DateFormat.WEEKDAY_DD_MMM -> {
                str.append(localDate.dayOfWeek).append(" ")
                    .append(localDate.dayOfMonth).append(" ")
                    .append(localDate.month.name.subSequence(0, 3))
            }
            DateFormat.WEEKDAY_DD_MMM_YYYY -> {
                str.append(localDate.dayOfWeek).append(" ")
                    .append(localDate.dayOfMonth).append(" ")
                    .append(localDate.month.name.subSequence(0, 3)).append(" ")
                    .append(localDate.year)
            }
        }
        return str.toString()
    }
}