package com.tamara.jettrivia.model

data class QuestionModel(
    val question: String?,
    val answer: String?,
    val category: String?,
    val choices: List<String>?
)