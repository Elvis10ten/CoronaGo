package com.coronago

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.coronago.geospatial.MovementManager
import com.coronago.quiz.Option
import com.coronago.quiz.QuizProvider
import com.coronago.rewards.RewardsManager
import com.coronago.setup.UserSetup
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.item_option.view.*

// Implicitly, maxing options at 26. We will never have that much anyways :)
private val OPTION_PREFIXES = ('A'..'Z').toList()

class QuizActivity : AppCompatActivity() {

    lateinit var userSetup: UserSetup
    lateinit var movementManager: MovementManager
    lateinit var rewardsManager: RewardsManager
    lateinit var quizProvider: QuizProvider

    private lateinit var selectedOption: Option
    private var lastSelectedOptionIndex: Int? = null
    private var lastOptionBackgroundTintList: ColorStateList? = null
    private var lastOptionTextColorList: ColorStateList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        setContentView(R.layout.activity_quiz)
        rewardsManager.onSecondChallengeStarted()
        showQuiz()
    }

    private fun showQuiz() {
        val quiz = quizProvider.get()
        quizQuestionText.text = quiz.question

        for((index, option) in quiz.options.withIndex()) {
            val optionView = layoutInflater.inflate(R.layout.item_option, null, false)

            val optionText = OPTION_PREFIXES[index] + ". " + option.text
            optionView.optionButton.text = optionText
            optionView.optionButton.setOnClickListener {
                lastSelectedOptionIndex?.let {
                    optionsContainer.getChildAt(it).optionButton.apply {
                        backgroundTintList = lastOptionBackgroundTintList
                        setTextColor(lastOptionTextColorList)
                    }
                }

                selectedOption = option
                lastSelectedOptionIndex = index
                lastOptionBackgroundTintList = optionView.optionButton.backgroundTintList
                lastOptionTextColorList = optionView.optionButton.textColors

                optionView.optionButton.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.purple_700))
                optionView.optionButton.setTextColor(Color.WHITE)

                confirmButton.isEnabled = true
            }

            optionsContainer.addView(optionView)
        }

        confirmButton.setOnClickListener { onOptionConfirmed() }
    }

    private fun onOptionConfirmed() {
        if(selectedOption.isCorrect) {
            rewardsManager.onSecondChallengePassed()
            AlertActivity.start(this, Alert(
                R.drawable.il_success,
                R.string.alertTitleSecondChallengePassed,
                R.string.alertSubtitleSecondChallengePassed,
                R.raw.sound_success,
                R.string.alertActionContinue,
                HomeActivity.getIntent(this)
            ))
            finish()
        } else {
            rewardsManager.onSecondChallengeFailed()
            AlertActivity.start(this, Alert(
                R.drawable.il_failure,
                R.string.alertTitleSecondChallengeFailed,
                R.string.alertSubtitleSecondChallengeFailed,
                R.raw.sound_failure,
                R.string.alertActionContinue,
                HomeActivity.getIntent(this)
            ))
            finish()
        }
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
