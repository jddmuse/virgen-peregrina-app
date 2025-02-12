package com.example.datepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDate

class DatePickerComponentHelper(
    private val daysOff: List<DatePickerDaysOffRange>
) {

    /** Eventos */
    private val _onDateSelected = MutableLiveData<LocalDate>()
    val onDateSelected: LiveData<LocalDate> get() = _onDateSelected

    /** Variables ****************************/
    val localDateNow = LocalDate.now()

    private var _localDateSelected: LocalDate = localDateNow
    val localDateSelected: LocalDate get() = _localDateSelected

    val monthSelected: Int get() = localDateSelected.monthValue
    val daySelected: Int get() = localDateSelected.dayOfMonth

    val daysOffDates: List<LocalDate> by lazy {
        val list = mutableListOf<LocalDate>()
        daysOff.forEach { range ->
            var i = range.start
            while(i.isBefore(range.end) || i.isEqual(range.end)) {
                list.add(i)
                i = i.plusDays(1)
            }
        }
        list
    }
    val daysOffByMonth: List<LocalDate> get() = run {
        daysOffDates.filter { it.monthValue == monthSelected }
    }

    fun valueChanged(value: Any, type: DatePickerTypeValueEnum) {
        when(type) {
            DatePickerTypeValueEnum.SELECTION -> {
                _localDateSelected = value as LocalDate
                _onDateSelected.value = localDateSelected
            }
            DatePickerTypeValueEnum.NEXT_MONTH -> {
                _localDateSelected = localDateSelected.plusMonths(1)
            }
            DatePickerTypeValueEnum.BACK_MONTH -> {
                _localDateSelected = localDateSelected.minusMonths(1)
            }
        }
    }

}