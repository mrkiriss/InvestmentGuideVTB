package com.example.investmentguidevtb.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investmentguidevtb.data.source.UserSegmentationDataManager
import com.example.investmentguidevtb.ui.profile.models.UserMessage
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

    private var _goal = MutableLiveData<String>()
    val goal: LiveData<String>
        get() = _goal

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

    fun getMainGoal() = viewModelScope.launch {
        val mainGoal = manager.getMainGoal()
        _goal.postValue(mainGoal)
    }

    sealed class Event() {
        object SegmentationPassed: Event()
        object SegmentationNotPassed: Event()
    }

}