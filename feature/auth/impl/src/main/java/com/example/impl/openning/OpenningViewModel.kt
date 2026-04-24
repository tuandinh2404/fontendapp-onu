package com.example.impl.openning

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OpenningViewModel: ViewModel() {
    val startDay =  (13 * 31) / 2 + (1..31).random()
    val startMonth =  (11 * 12) / 2 + (1..12).random()

    var selectedDay by mutableStateOf(1)
        private set
    var selectedMonth by mutableStateOf(1)
        private set
    var hasInteracted by mutableStateOf(false)
        private set
    var hasContended by mutableStateOf(false)
    private set

    fun onDayChanged(day: Int) { selectedDay = day }
    fun onMonthChanged(month: Int) { selectedMonth = month }
    fun onInteracted() { hasInteracted = true }
    fun onContended() { hasContended = true }
}
