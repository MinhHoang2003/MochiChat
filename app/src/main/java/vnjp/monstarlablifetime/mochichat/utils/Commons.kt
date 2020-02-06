package vnjp.monstarlablifetime.mochichat.utils

import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

object Commons {

    private var DATE_FORMAT_T: String = "EEE, MMM dd"
    private var K_DATE_FORMAT: String = "MMM dd"
    private var DATE_FORMAT_D: String = "dd/MM/yyyy"
    private var CURRENT_FORMAT: String = "HH:mm a"


    fun getStringCurrentDateTime(date: Date): String? {
        val formatter = SimpleDateFormat(
            CURRENT_FORMAT,
            Locale.US
        )
        return formatter.format(date)
    }

    fun getCurrentDateTime(dateCurrent: Date): String? {
        val formatter = SimpleDateFormat(
            K_DATE_FORMAT,
            Locale.US
        )
        return formatter.format(dateCurrent)
    }

    fun getDateTimeCurrent(datetime: Date): String? {
        val formatter = SimpleDateFormat(
            DATE_FORMAT_D,
            Locale.US
        )
        return formatter.format(datetime)
    }
}