package com.tamara.jettrivia.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tamara.jettrivia.screens.QuestionsViewModel
import com.tamara.jettrivia.util.Constants

@Composable
fun Questions(
    modifier: Modifier = Modifier,
    viewModel: QuestionsViewModel
) {
    val questions = viewModel.questions()

    if (viewModel.isLoading()){
        CircularProgressIndicator(
            modifier = modifier
        )
    } else {
        for(question in questions){
            Log.d(Constants.APP_NAME, "Question: ${question.question} - Answer ${question.answer}")
        }
    }
}
