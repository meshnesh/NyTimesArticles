package com.munene.nyTimePost.viewmodel

import com.munene.nyTimePost.helper.Resource
import com.munene.nyTimePost.model.ArticleResponse
import com.munene.nyTimePost.network.ApiDataSource
import com.munene.nyTimePost.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepo @Inject constructor(private val apiDataSource: ApiDataSource) : BaseDataSource() {

    //Using coroutines flow to get the response from

    suspend fun getPopularArticles(): Flow<Resource<ArticleResponse>> {
        return flow {
            emit(safeApiCall { apiDataSource.getPopularArticles() })
        }.flowOn(Dispatchers.IO)
    }
}