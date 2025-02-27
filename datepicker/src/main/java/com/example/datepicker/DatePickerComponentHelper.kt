package com.example.datepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDate
import java.time.temporal.ChronoField

class DatePickerComponentHelper(
    private val daysOff: List<DatePickerDaysOffRange>
) {

    /** Eventos */
    private val _onDateSelectedEvent = MutableLiveData<LocalDate>()
    val onDateSelectedEvent: LiveData<LocalDate> get() = _onDateSelectedEvent

    private val _onClearBeforeSelection = MutableLiveData<Int>()
    val onClearBeforeSelection: LiveData<Int> get() = _onClearBeforeSelection

    /** Variables ****************************/
//    val localDateNow = LocalDate.now()

    private var _localDateSelected: LocalDate = LocalDate.now()
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
                val daySelected = value as Int
                val dateSelected = localDateSelected.with(ChronoField.DAY_OF_MONTH, daySelected.toLong())
                /** Limpia la seleccion solo si la nueva seleccion es del mismo mes */
                if(dateSelected.month == onDateSelectedEvent.value?.month) {
                    _onClearBeforeSelection.value = localDateSelected.dayOfMonth
                }
                _localDateSelected = dateSelected
                _onDateSelectedEvent.value = localDateSelected
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