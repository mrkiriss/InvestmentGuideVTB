package com.example.investmentguidevtb.ui.practice

import android.util.Log
import androidx.lifecycle.*
import com.example.investmentguidevtb.data.repository.PracticeRepository
import com.example.investmentguidevtb.ui.Event
import com.example.investmentguidevtb.ui.practice.models.GameArticle
import com.example.investmentguidevtb.ui.practice.models.GameSituation
import com.example.investmentguidevtb.ui.practice.models.GameSolution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val rep: PracticeRepository
): ViewModel(){

    private val _requestToUpdateSituation = MutableLiveData<GameSituation>()
    val requestToUpdateSituation: LiveData<GameSituation> = _requestToUpdateSituation

    private val _requestToShowStepFeedback = MutableLiveData<Event<GameSolution>>()
    val requestToShowStepFeedback: LiveData<Event<GameSolution>> = _requestToShowStepFeedback

    private val _requestToShowToastContent = MutableLiveData<String>()
    val requestToShowToastContent: LiveData<String> = _requestToShowToastContent

    val requestToChangeCardVisible: LiveData<Boolean> = rep.requestToChangeCardVisible

    private fun loadGameSituation() {
        viewModelScope.launch {
            val result = rep.requestSolutionFromServer()
            if (result == null) {
                if (_requestToShowStepFeedback.value != null) _requestToUpdateSituation.value = _requestToUpdateSituation.value
                _requestToShowToastContent.value = "Загрузка не удалась, повторите попытку позже"
            } else {
                _requestToUpdateSituation.value = result!!
            }
        }
    }

    fun processSelectedSolution(solutionIndex: Int) {
        val selectedSolution = requestToUpdateSituation.value?.solutions?.get(solutionIndex)

        if (selectedSolution == null) {
            // пытаемся загрузить данные, если пользователь карточку передвинул, а выборов нет
            loadGameSituation()
        } else {
            // создание фидбека при наличии контента
            selectedSolution.feedback?.also {
                _requestToShowStepFeedback.value = Event(selectedSolution!!)
            }

            // обработка с целью получить новую карточку
            viewModelScope.launch {
                rep.processSelectedSolution(selectedSolution)
            }
        }
    }
}