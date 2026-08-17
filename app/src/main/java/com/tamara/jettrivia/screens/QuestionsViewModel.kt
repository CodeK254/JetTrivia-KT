package com.tamara.jettrivia.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamara.jettrivia.data.DataOrException
import com.tamara.jettrivia.model.QuestionModel
import com.tamara.jettrivia.repository.QuestionRepository
import com.tamara.jettrivia.util.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

enum class Function{
    NEXT,
    BACK,
}

data class TriviaState(
    val currentIndex: Int = 0,
    val correctAnswers: Int = 0
)

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository): ViewModel() {
    val data: MutableState<DataOrException<ArrayList<QuestionModel>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    private val _currentIndex = mutableIntStateOf(0)
    private val _questions = mutableListOf<QuestionModel>()
    private val _answer = mutableStateOf("")

    init {
        getAllQuestions()
    }

    fun questions(): List<QuestionModel> {
        return _questions
    }

    fun correctAnswer(
        question: QuestionModel,
        answer: String,
    ): Boolean {
        return question.answer == answer
    }

    fun currentAnswer(): String {
        return _answer.value
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
        logger("Answer is - ${_answer.value}")
        if(_answer.value.isNotEmpty()) {
            if (func == Function.BACK) {
                logger("Checking back - ${_currentIndex.intValue > 0}")
                if (_currentIndex.intValue > 0) {
                    _currentIndex.intValue -= 1
                }
            } else {
                logger("Checking next - ${_currentIndex.intValue < (getMax() - 1)}")
                if (_currentIndex.intValue < (getMax() - 1)) {
                    _currentIndex.intValue += 1
                }
            }
            _answer.value = ""
        }
    }

    fun setAnswer(answer: String) {
        _answer.value = answer
    }

    fun getCurrentIndex(): Int {
        return _currentIndex.intValue
    }
}