package com.munene.nyTimePost.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munene.nyTimePost.helper.Resource
import com.munene.nyTimePost.helper.SingleLiveEvent
import com.munene.nyTimePost.model.ArticleResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val mainRepo: MainRepo) : ViewModel() {

    //cached
    private val _articleResponseData = SingleLiveEvent<Resource<ArticleResponse>>()

    val articleResponseData = _articleResponseData

    val articleTitle = MutableLiveData<String>()
    val articleTime = MutableLiveData<String>()
    val articleDate = MutableLiveData<String>()
    val articleAuthor = MutableLiveData<String>()
    val articleSource = MutableLiveData<String>()
    val articleUrl = MutableLiveData<String>()

    //Public function to get the result of popular articles

    fun getPopularArticles() {
        viewModelScope.launch {
            mainRepo.getPopularArticles().collect {
                articleResponseData.value = it
            }
        }
    }
}