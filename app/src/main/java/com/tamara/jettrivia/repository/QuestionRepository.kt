package com.tamara.jettrivia.repository

import android.util.Log
import com.tamara.jettrivia.data.DataOrException
import com.tamara.jettrivia.model.QuestionModel
import com.tamara.jettrivia.network.QuestionAPI
import com.tamara.jettrivia.util.Constants
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionAPI) {
    private val dataOrException =
        DataOrException<ArrayList<QuestionModel>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionModel>, Boolean, java.lang.Exception>{
        try{
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()

            if(dataOrException.data.toString().isNotEmpty()){
                dataOrException.loading = false
            }
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e(Constants.APP_NAME, "getAllQuestions: ", exception)
        } finally {
            dataOrException.loading = false
        }

        return dataOrException
    }
}