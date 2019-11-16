package com.example.quizlet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.example.quizlet.model.ContextHolder
import kotlinx.android.synthetic.main.activity_submit_test.*

class SubmitTestActivity : AppCompatActivity() {
    var answerService = AnswerService(ContextHolder.questions)
    var studentResult = ContextHolder.studentResult
    var numberOfCorrectAnswers: Int = 0
    private var numberOfQuestions: Int = ContextHolder.questions.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_test)
        setUI()
    }

    fun getNofCorrectAnswers() {
        studentResult.choices.flatMap { it.answers }.map { answerService.incrementIfIdsAreEqual(it) }
        numberOfCorrectAnswers = AnswerService.noCorrectAnswers
    }

    fun getResult(): CharSequence {
        getNofCorrectAnswers()
        return ((numberOfCorrectAnswers.toDouble() / numberOfQuestions.toDouble()) * 100).toString() + "%"
    }

    fun setUI() {
        scoreTextView.text = getResult()
    }

    @Override
    override fun onBackPressed() {
        val i = Intent(this, SubmitTestActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        finish()
    }

    @Override
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            moveTaskToBack(false)
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}
