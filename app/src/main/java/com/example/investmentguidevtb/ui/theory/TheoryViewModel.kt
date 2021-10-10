package com.example.investmentguidevtb.ui.theory

import android.util.Log
import androidx.lifecycle.*
import com.example.investmentguidevtb.data.source.UserSegmentationDataManager
import com.example.investmentguidevtb.data.source.api.ArticleApi
import com.example.investmentguidevtb.ui.practice.models.GameArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class TheoryViewModel @Inject constructor(
    private val articleApi: ArticleApi
) : ViewModel(){

    val requestAddList = MutableLiveData<List<GameArticle>>()
    val requestToast = MutableLiveData<String>()


    init {
        viewModelScope.launch {
            try {
                val result = articleApi.downloadAllArticles()
                 if (result.isSuccessful && result.body() != null) {
                     requestAddList.value = result.body()
                 } else {
                     requestToast.value = "Загрузка статей завершилась неудачей"
                 }
            } catch (e: Exception) {
                requestToast.value = "Загрузка статей завершилась неудачей"
                e.printStackTrace()
            }
        }
    }
}