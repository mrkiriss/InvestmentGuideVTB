package com.example.investmentguidevtb.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.investmentguidevtb.data.source.api.ArticleApi
import com.example.investmentguidevtb.data.source.api.PracticeApi
import com.example.investmentguidevtb.ui.practice.models.GameEndFeedback
import com.example.investmentguidevtb.ui.practice.models.GameSituation
import javax.inject.Inject

class PracticeRepository @Inject constructor(
    private val practiceApi: PracticeApi,
    private val articleApi: ArticleApi,
    private val feedback: GameEndFeedback
) {

    val requestToChangeCardVisible = MutableLiveData<Boolean>()
    val requestToEndGame = MutableLiveData<GameEndFeedback>()


    suspend fun requestSolutionFromServer(risk: Float, difficult: Float, capitalDiff: Int, vtb: Int): GameSituation? {

        try {
            requestToChangeCardVisible.value = false
            val result = practiceApi.getSolution(
                difficult.toString(),
                vtb.toString(),
                capitalDiff.toString(),
                risk.toString()
            )

            Log.i(
                "retrofitTest",
                "result successful = ${result.isSuccessful}, result data = ${result.body()}"
            )

            requestToChangeCardVisible.value = true

            return if (result.isSuccessful) result.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    suspend fun requestEndFeedback(risk: Float, currentCapital: Int, numberOfDaysFromBeginningToEnd: Int) {

    }
}