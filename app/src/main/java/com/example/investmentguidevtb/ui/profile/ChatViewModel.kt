package com.example.investmentguidevtb.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investmentguidevtb.data.repository.ProfileRepository
import com.example.investmentguidevtb.ui.profile.models.Answer
import com.example.investmentguidevtb.ui.profile.models.ChatQuestionsResponse
import com.example.investmentguidevtb.ui.profile.models.Question
import com.example.investmentguidevtb.ui.profile.models.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

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

    fun getQuestions() = viewModelScope.launch {
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
            if(curQuestionId >= it.size) return

            val ques = it.get(curQuestionId)
            val ans = it.get(curQuestionId).answers.get(answerId)
            Log.d("ChatViewModel", "Answer id is: "+answerId)

            val answerMessage = UserMessage(messageCounter, 1, ans.text)
            _chatState.add(answerMessage)
            chatState.postValue(_chatState)

            messageCounter++
            curQuestionId++
            if(curQuestionId >= it.size) return

            val questionMessage = UserMessage(messageCounter, 2, ques.text+"\nВарианты ответа:\n"+getReadableAnswers(ques.answers))
            _chatState.add(questionMessage)
            messageCounter++
            chatState.postValue(_chatState)
        }
    }


}