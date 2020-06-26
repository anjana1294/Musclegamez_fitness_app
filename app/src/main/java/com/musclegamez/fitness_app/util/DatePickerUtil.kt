package com.musclegamez.fitness_app.util
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.util.*


object DatePickerUtil {


    fun openDatePicker(context: Context, onGetMilliSeconds: (milliseconds: Long) -> Unit) {
        var cal = Calendar.getInstance()
        var datePickerDialog = DatePickerDialog(
            context, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    view: DatePicker, year: Int, monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    onGetMilliSeconds(cal.timeInMillis)
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = Date().time
        datePickerDialog.show()
    }


    fun openTimePicker(context: Context, onGetMilliSeconds: (time: String) -> Unit) {
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
                context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var time = hourOfDay.toString() + ":" + minute
            onGetMilliSeconds(time)
        },
                mHour,
                mMinute,
                false
        )
        timePickerDialog.show()
    }
}
