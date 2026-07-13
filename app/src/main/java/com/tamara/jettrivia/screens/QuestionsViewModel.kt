package com.tamara.jettrivia.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamara.jettrivia.component.Questions
import com.tamara.jettrivia.data.DataOrException
import com.tamara.jettrivia.model.Question
import com.tamara.jettrivia.model.QuestionModel
import com.tamara.jettrivia.repository.QuestionRepository
import com.tamara.jettrivia.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

enum class Function{
    NEXT,
    BACK,
}

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository): ViewModel() {
    val data: MutableState<DataOrException<ArrayList<QuestionModel>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    private val _currentIndex = mutableIntStateOf(0)
    private val _questions = mutableListOf<QuestionModel>()

    init {
        getAllQuestions()
    }

    fun questions(): List<QuestionModel> {
        return _questions
    }

    fun setQuestions(questions: List<QuestionModel>){
        _questions.addAll(
            elements = questions
        )
    }

    fun isLoading(): Boolean {
        return data.value.loading ?: false
    }

    fun getMax(): Int {
        return _questions.size
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestions()
            if (data.value.data.toString().isNotEmpty()){
                setQuestions(data.value.data?.toList() ?: emptyList())
                data.value.loading = false
            }
        }
    }

    fun toggleQuestions(func: Function){
        if(func == Function.BACK){
            if (_currentIndex.intValue > 0) {
                _currentIndex.intValue -= 1
            }
        } else {
            if (_currentIndex.intValue < (getMax() - 1)) {
                _currentIndex.intValue -= 1
            }
        }
    }
}