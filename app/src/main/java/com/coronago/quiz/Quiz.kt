package com.coronago.quiz

data class Quiz(
    val question: String,
    var options: Set<Option>
)

data class Option(
    val text: String,
    val isCorrect: Boolean
)