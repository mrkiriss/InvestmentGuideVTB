package com.example.investmentguidevtb.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investmentguidevtb.data.repository.ProfileRepository
import com.example.investmentguidevtb.data.source.UserSegmentationDataManager
import com.example.investmentguidevtb.ui.profile.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val manager: UserSegmentationDataManager,
    private val repository: ProfileRepository
) : ViewModel() {

    private var parameters = SegmentationParameters()

    private val eventChannel = Channel<Event>()
    val event = eventChannel.receiveAsFlow()

    private var curQuestionId: Int = 0
    private var messageCounter: Int = 0

    private var questions: List<Question> = listOf()

    private  var _chatState: MutableList<UserMessage> = mutableListOf()
    private var chatState = MutableLiveData<List<UserMessage>>(listOf())
    val chatStateData: LiveData<List<UserMessage>>
        get() = chatState

    init{
        getQuestions()
    }

    private fun getQuestions() = viewModelScope.launch {
        try{
            val response = repository.getQuestions()
            questions = response.questions

            if (!questions.isNullOrEmpty()){
                val ques = questions.get(curQuestionId)
                _chatState.add(UserMessage(messageCounter, 2, ques.text+"\nВарианты ответа:\n"+getReadableAnswers(ques.answers)))
                messageCounter++
                chatState.postValue(_chatState)
            }
        }catch(t: Throwable){
            Log.d("ChatViewModel", t.message.toString())
        }

    }

    private fun getReadableAnswers(answers: List<Answer>): String {
        var result = ""
        for(answer in answers) {
            result+=answer.text+"\n"
        }
        return result
    }

    fun answerQuestion(answerId: Int) {
        questions?.let {
            if(curQuestionId >= it.size) {
                finishSegmentation()
                return
            }

            // get current question and possible answers
            val ques = it.get(curQuestionId)
            val ans = ques.answers.get(answerId)

            // change parameters based on user's answer
            for(param in ans.parameter){
                when(param.name){
                    "age" -> parameters.age+=param.change.toFloat()
                    "edu" -> parameters.edu+=param.change.toFloat()
                    "riskReadiness" -> parameters.riskReadiness+=param.change.toFloat()
                    "knowledge" -> parameters.knowledge+=param.change.toFloat()
                    "goal" -> parameters.goal+=param.change.toFloat()
                    "salary" -> parameters.salary+=param.change.toFloat()
                    "foundOut" -> parameters.foundOut+=param.change.toFloat()
                }
            }

            // update recycler view with answer
            val answerMessage = UserMessage(messageCounter, 1, ans.text)
            _chatState.add(answerMessage)
            chatState.postValue(_chatState)
            messageCounter++

            curQuestionId++
            if(curQuestionId >= it.size) {
                finishSegmentation()
                return
            }

            // update recycler view with question
            val questionMessage = UserMessage(messageCounter, 2, ques.text+"\nВарианты ответа:\n"+getReadableAnswers(ques.answers))
            _chatState.add(questionMessage)
            messageCounter++
            chatState.postValue(_chatState)
        }
    }

    private fun finishSegmentation() = viewModelScope.launch {

        //Formulas
        val difficulty = (parameters.edu + parameters.knowledge + parameters.goal + parameters.salary + parameters.foundOut) * 3f / 5f
        val risk = parameters.riskReadiness

        //Saving to DB
        manager.setDifficulty(difficulty)
        manager.setRisk(risk)
        manager.setSegmentationPassed()
        eventChannel.send(Event.navigateToGameStart)

    }

    sealed class Event() {
        object navigateToGameStart: Event()
    }

}