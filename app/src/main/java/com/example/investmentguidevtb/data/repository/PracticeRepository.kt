package com.example.investmentguidevtb.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.investmentguidevtb.data.source.api.PracticeApi
import com.example.investmentguidevtb.ui.practice.models.GameSituation
import javax.inject.Inject

class PracticeRepository @Inject constructor(
    private val practiceApi: PracticeApi
) {

    val requestToChangeCardVisible = MutableLiveData<Boolean>()

    suspend fun requestSolutionFromServer(risk: Float, difficult: Float, capitalDiff: Int, vtb: Int): GameSituation? {

        requestToChangeCardVisible.value = false
        val result = practiceApi.getSolution(
            difficult.toString(),
            vtb.toString(),
            capitalDiff.toString(),
            risk.toString()
        )

        Log.i("retrofitTest", "result successful = ${result.isSuccessful}, result data = ${result.body()}")

        requestToChangeCardVisible.value = true

        return if (result.isSuccessful) result.body() else null
    }
}