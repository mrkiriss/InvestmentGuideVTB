package com.example.investmentguidevtb.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investmentguidevtb.data.source.UserSegmentationDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val manager: UserSegmentationDataManager
) : ViewModel() {

    private val eventChannel = Channel<Event>()
    val event = eventChannel.receiveAsFlow()

    init {
        checkIfSegmentationPassed()
    }

    fun checkIfSegmentationPassed() = viewModelScope.launch {
        val passed = manager.getSegmentationPassed()
        if(passed){
            eventChannel.send(Event.SegmentationPassed)
        }else{
            eventChannel.send(Event.SegmentationNotPassed)
        }
    }

    sealed class Event() {
        object SegmentationPassed: Event()
        object SegmentationNotPassed: Event()
    }

}