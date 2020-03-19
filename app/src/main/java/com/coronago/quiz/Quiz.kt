package com.coronago.quiz

data class Quiz(
    val question: String,
    val options: Set<Option>
)

data class Option(
    val text: String,
    val isCorrect: Boolean
)