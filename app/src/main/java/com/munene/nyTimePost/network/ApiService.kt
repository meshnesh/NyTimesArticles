package com.munene.nyTimePost.network

import com.munene.nyTimePost.helper.EndPoints
import com.munene.nyTimePost.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("7.json?api-key=" + EndPoints.ARTICLES_API_KEY)
    suspend fun getPopularArticles(
    ): Response<ArticleResponse>
}
