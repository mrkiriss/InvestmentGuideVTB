package com.example.investmentguidevtb.ui.theory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.investmentguidevtb.data.source.UserSegmentationDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheoryViewModel @Inject constructor(
    private val manager: UserSegmentationDataManager
) : ViewModel(){

    //FOR TESTING
    val data: LiveData<String> = liveData {  }

    init{
        viewModelScope.launch {
            Log.d("TheoryViewModel", "Difficulty: "+manager.getDifficulty())
            Log.d("TheoryViewModel", "Risk: "+manager.getRisk())
        }

    }
}