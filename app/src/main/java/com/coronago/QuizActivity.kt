package com.coronago

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coronago.geospatial.MovementManager
import com.coronago.quiz.QuizProvider
import com.coronago.rewards.RewardsManager
import com.coronago.setup.UserSetup

class QuizActivity : AppCompatActivity() {

    lateinit var userSetup: UserSetup
    lateinit var movementManager: MovementManager
    lateinit var rewardsManager: RewardsManager
    lateinit var quizProvider: QuizProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        setContentView(R.layout.activity_quiz)
        showQuiz()
    }

    private fun showQuiz() {
        rewardsManager.onSecondChallengeStarted()
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(getIntent(context))
        }

        fun getIntent(context: Context): Intent {
            return Intent(context, QuizActivity::class.java)
        }
    }
}
