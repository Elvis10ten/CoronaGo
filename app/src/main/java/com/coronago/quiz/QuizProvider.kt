package com.coronago.quiz

import android.content.SharedPreferences

private val bank = listOf(
    Quiz(
        "When was the first case of Corona detected",
        setOf(
            Option("01 March 2020", false),
            Option("14 February 2020", false),
            Option("31 December 2019", false),
            Option("10 January 2020", false)
        )
    )
)

private const val KEY_LAST_QUIZ_INDEX = "KEY_LAST_QUIZ_INDEX"

class QuizProvider(
    private val sp: SharedPreferences
) {

    fun get(): Quiz {
        val quizIndex = getNextQuizIndex()
        val quiz = bank[quizIndex]
        updateLastQuizIndex(quizIndex)
        return quiz
    }

    private fun getNextQuizIndex(): Int {
        return (getLastQuizIndex() + 1) % bank.size
    }

    private fun getLastQuizIndex() = sp.getInt(KEY_LAST_QUIZ_INDEX, 0)

    private fun updateLastQuizIndex(lastIndex: Int) {
        sp.edit()
            .putInt(KEY_LAST_QUIZ_INDEX, lastIndex)
            .apply()
    }
}