package com.tamara.jettrivia.network

import com.tamara.jettrivia.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {
    @GET("general.json")
    suspend fun getAllQuestions(): Question
}