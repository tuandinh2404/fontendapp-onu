package com.example.moments

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

@HiltViewModel
class MomentsViewModel @Inject constructor(

): ViewModel()  {
    var hasInteracted by mutableStateOf(false)
    private set

    fun initState() {
        hasInteracted = true
    }
}