package com.example.datepicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import com.example.datepicker.util.DateFormat
import com.example.datepicker.util.DateUtils
import com.example.datepickercomponentview.databinding.DatePickerComponentViewBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoField

class DatePickerComponentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    companion object {
        private const val TAG = "DatePicker"
    }

    private lateinit var helper: DatePickerComponentHelper

    private var binding: DatePickerComponentViewBinding =
        DatePickerComponentViewBinding.inflate(LayoutInflater.from(context), this, true)

    /** Variables */
//    private val daysOff: List<DatePickerDaysOffRange> = listOf(
//        DatePickerDaysOffRange(LocalDate.now(), LocalDate.now().with(ChronoField.DAY_OF_MONTH, 16)),
//        DatePickerDaysOffRange(LocalDate.now().plusMonths(1).with(ChronoField.DAY_OF_MONTH, 4), LocalDate.now().plusMonths(1).with(ChronoField.DAY_OF_MONTH, 8)),
//        DatePickerDaysOffRange(LocalDate.now().plusMonths(1).with(ChronoField.DAY_OF_MONTH, 26), LocalDate.now().plusMonths(2).with(ChronoField.DAY_OF_MONTH, 4)),
//    )
    private val daysOff: MutableList<DatePickerDaysOffRange> = mutableListOf()
    private var onDateSelectedListener: ((LocalDate) -> Unit)? = null

    init {
//        initDefaultSettings()
//
//        initView()
//        initObservers()
//        initListeners()
    }

    private fun initDefaultSettings() {
        helper = DatePickerComponentHelper(daysOff)

        /** Pinte titulos de dias (Ejemplo: lun, mar, mie, jue, vie, sab, dom) */
        val weekDays = DayOfWeek.values().map { it.name.subSequence(0, 2) }
        for (day in weekDays) {
            val dayOfWeek = TextView(context).apply {
                text = day
                textAlignment = TEXT_ALIGNMENT_CENTER
                typeface = Typeface.DEFAULT_BOLD
                setTextColor(Color.BLACK)
                setLineSpacing(20f, 1f)
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setGravity(Gravity.FILL)
                    setPadding(0, 8, 0, 8)
                }
            }
            binding.calendarDaysOfWeekGridLayout.addView(dayOfWeek)
        }
    }

    private fun initView() {
        /** Limpia el grid layout */
        binding.calendarGridLayout.removeAllViews()

        /** Pinte titulo mes año (Ejemplo: ENE 2025) */
        binding.monthYearTextView.text = getMonthYear()

        /** Pinte dias del mes (Ejemplo: 1, 2, 3, 4, 5..N) */
        val firstDayOfMonthDate = helper.localDateSelected.with(ChronoField.DAY_OF_MONTH, 1)
        val dayOfWeek = firstDayOfMonthDate.get(ChronoField.DAY_OF_WEEK)
        val daysOfMonth = firstDayOfMonthDate.lengthOfMonth()
        paintDaysOfMonth(dayOfWeek - 1, daysOfMonth)

    }

    private fun getMonthYear(): String {
        val month = helper.localDateSelected.month.name.subSequence(0, 3).toString().uppercase()
        return "$month ${helper.localDateSelected.year}"
    }

    private fun paintDaysOfMonth(startColumn: Int, daysOfMonth: Int) {
        val daysOff = helper.daysOffByMonth.map { it.dayOfMonth }
        for (i in 1..daysOfMonth) {
            val dayOfMonth = TextView(context).apply {
                text = "$i"
                textAlignment = TEXT_ALIGNMENT_CENTER
                setTextColor(Color.BLACK)
                setBackgroundColor(Color.TRANSPARENT)
                setLineSpacing(20f, 1f)
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(if (i == 1) startColumn else GridLayout.UNDEFINED, 1f)
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setGravity(Gravity.FILL)
                    setPadding(0, 8, 0, 8)
                }
                if(daysOff.contains(i)) {
                    setBackgroundColor(Color.GRAY)
                } else {
                    setOnClickListener {
                        /** Limpie seleccion anterior */
                        try {
                            val indexOf = helper.localDateSelected.dayOfMonth - 1
                            val textView = binding.calendarGridLayout.getChildAt(indexOf) as TextView
                            textView.setBackgroundColor(Color.TRANSPARENT)
                            // falta excluir seleccion de un mes diferente al actual
                        } catch (ex:Exception) {
                            Log.e(TAG, "onDateSelected -> $ex")
                        }
                        /** Setea nueva seleccion */
                        val date = helper.localDateSelected.with(ChronoField.DAY_OF_MONTH, i.toLong())
                        helper.valueChanged(date, DatePickerTypeValueEnum.SELECTION)
                        setBackgroundColor(Color.BLUE)
                    }
                }
            }
            binding.calendarGridLayout.addView(dayOfMonth)
        }
    }

    private fun initObservers() {
        helper.onDateSelected.observe(context as LifecycleOwner) { date: LocalDate ->
            binding.editText.setText(DateUtils.format(date, DateFormat.WEEKDAY_DD_MMM_YYYY))
            onDateSelectedListener?.invoke(date)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        binding.arrowBackImageButton.setOnClickListener {
            helper.valueChanged(1, DatePickerTypeValueEnum.BACK_MONTH)
            initView()
        }
        binding.arrowNextImageButton.setOnClickListener {
            helper.valueChanged(1, DatePickerTypeValueEnum.NEXT_MONTH)
            initView()
        }
        binding.editText.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_UP) {
                v.requestFocus()
                return@setOnTouchListener true
            }
            false
        }
        binding.editText.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                val imm = getSystemService(context, InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                binding.calendarLayout.visibility = View.VISIBLE
            } else {
                binding.calendarLayout.visibility = View.GONE
            }
        }
    }



    /**** Public functions ***********************************/
    /*********************************************************/

    fun setDaysOff(list: List<DatePickerDaysOffRange>): DatePickerComponentView {
        return run {
            daysOff.clear()
            daysOff.addAll(list)
            this@DatePickerComponentView
        }
    }

    fun setOnDateSelectedListener(listener: (LocalDate) -> Unit): DatePickerComponentView {
        return run {
            onDateSelectedListener = listener
            this@DatePickerComponentView
        }
    }

    fun build() {
        initDefaultSettings()

        initView()
        initObservers()
        initListeners()
    }

}