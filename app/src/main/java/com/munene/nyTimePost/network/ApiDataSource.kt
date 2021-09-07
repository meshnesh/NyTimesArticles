package com.munene.nyTimePost.network

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularArticles() =
        apiService.getPopularArticles()
}