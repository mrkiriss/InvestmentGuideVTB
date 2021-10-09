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

    var prevCapital = 0
    var currentCapital = 0
    var risk = 0f
    var difficult = 0f
    var vtb = 2
    var inflation = 0.05f

    private val _requestToUpdateSituation = MutableLiveData<GameSituation>()
    val requestToUpdateSituation: LiveData<GameSituation> = _requestToUpdateSituation

    private val _requestToShowStepFeedback = MutableLiveData<Event<GameSolution>>()
    val requestToShowStepFeedback: LiveData<Event<GameSolution>> = _requestToShowStepFeedback

    private val _requestToShowToastContent = MutableLiveData<String>()
    val requestToShowToastContent: LiveData<String> = _requestToShowToastContent

    val requestToChangeCardVisible: LiveData<Boolean> = rep.requestToChangeCardVisible

    val requestToUpdateCapital: MutableLiveData<Int> = MutableLiveData<Int>()


    private fun loadGameSituation() {
        viewModelScope.launch {
            val result = rep.requestSolutionFromServer(risk, difficult, currentCapital - prevCapital, vtb)
            if (result == null) {
                _requestToShowToastContent.value = "Загрузка провалилась, повторите попытку позже"
            } else {
                _requestToUpdateSituation.value = result!!
                startInflation()
            }
        }
    }

    private fun startInflation() {
        currentCapital = (currentCapital * (1 - inflation)).toInt()
        requestToUpdateCapital.value = currentCapital
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

            // обработка
            risk = (risk + selectedSolution.delta.risk) / 2
            vtb -= if (selectedSolution.delta.vtb) 1 else 0
            prevCapital = currentCapital
            currentCapital += selectedSolution.delta.capital

            // запрос на загрузку новой карточки
            loadGameSituation()
        }
    }
}