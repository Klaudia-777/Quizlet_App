package com.example.quizlet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.example.quizlet.model.ContextHolder
import com.example.quizlet.model.Question
import com.example.quizlet.activity.QuizletRmoteService
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class StudentLoginActivity : AppCompatActivity() {
    lateinit var quizletRmoteService: QuizletRmoteService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizletRmoteService = QuizletRmoteService.get()
        startQuizButton.setOnClickListener { onStartClick() }
    }

    fun onStartClick() {
        val testId = quizIdET.text.toString()
        ContextHolder.testId = testId
        ContextHolder.albumNumber = albumNumberET.text.toString()
        val allQuestions = quizletRmoteService.getQuestions(testId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::startTest)
    }

    fun startTest(questions: List<Question>) {
        ContextHolder.questions = questions
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)

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
