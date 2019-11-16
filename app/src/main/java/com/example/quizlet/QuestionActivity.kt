package com.example.quizlet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.CheckBox
import com.example.quizlet.model.ContextHolder
import com.example.quizlet.model.Question
import com.example.quizlet.model.StudentChoice
import com.example.quizlet.model.StudentResult
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    var index = 0
    lateinit var questionsList: List<Question>
    val studentChoices: MutableList<StudentChoice> = mutableListOf()
    lateinit var currentQuestion: Question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        previousButton.setOnClickListener { onPreviousButton() }
        nextButton.setOnClickListener { onNextButton() }
        finishTestButton.setOnClickListener { onFinishButtonClicked() }
        questionsList = ContextHolder.questions
        setUI(questionsList[0])
    }

    fun onPreviousButton() {
        if (index == questionsList.size - 1) {
            finishTestButton.visibility = View.INVISIBLE
        }
        if (index > 0) {
            index--
            saveCurrentChoice()
            setUI(questionsList[index])
        }
    }

    fun onNextButton() {
        if (index == questionsList.size - 2) {
            finishTestButton.visibility = View.VISIBLE
        }
        if (index < questionsList.size - 1) {
            index++
            saveCurrentChoice()
            setUI(questionsList[index])
        }
    }

    fun onFinishButtonClicked() {
        saveCurrentChoice()
        ContextHolder.studentResult =
            StudentResult(albumNumber = ContextHolder.albumNumber, choices = studentChoices)
        setFinishUI()
    }

    fun saveCurrentChoice() {
        val chosenAnswerIds = (0 until answersLayout.childCount)
            .asSequence()
            .mapNotNull { answersLayout.getChildAt(it) }
            .map { it as CheckBox }
            .filter { it.isChecked }
            .map { currentQuestion.findAnswerIdByText(it.text.toString()) }
            .toMutableList()
        studentChoices.removeIf { it.questionId == currentQuestion.id }
        studentChoices.add(StudentChoice(currentQuestion.id, chosenAnswerIds))
    }

    fun Question.findAnswerIdByText(text: String) = answers.find { it.text == text }!!.id

    fun List<StudentChoice>.findByQuestionId(id: String) = find { it.questionId == id }

    fun setUI(question: Question) {
        val choices = studentChoices.findByQuestionId(question.id)
        currentQuestion = question
        answersLayout.removeAllViews()
        questionTextView.text = question.question
        question.answers.forEach { answer ->
            CheckBox(this).apply {
                text = answer.text
                choices?.takeIf { it.answers.contains(answer.id) }
                    ?.let { this.isChecked = true }
                answersLayout.addView(this)
            }
        }

    }

    fun setFinishUI() {
        val intent = Intent(this, SubmitTestActivity::class.java)
        startActivity(intent)
    }

    @Override
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onPreviousButton()
            return false
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            moveTaskToBack(false)
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}
