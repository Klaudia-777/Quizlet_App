package com.example.quizlet

import com.example.quizlet.model.Answer
import com.example.quizlet.model.Question

class AnswerService(val allTheQuestions: List<Question>) {

    companion object {
        var noCorrectAnswers = 0
    }

    fun getAllTheCorrectAnswersFromQuestions(): MutableList<Answer> {
        val correct =allTheQuestions.flatMap{ it.answers }
        val correctAnswers=correct.map{it}.filter { it.isCorrect }.toMutableList()
        return correctAnswers
    }

    fun getAnswerIdForAllTheAnswers(): List<String> {
        val answerObjects = getAllTheCorrectAnswersFromQuestions()
        return answerObjects.map { it.id }
    }

    fun incrementIfIdsAreEqual(id: String) {
        val allAnswerIds = getAnswerIdForAllTheAnswers()
        if (allAnswerIds.contains(id)) {
            noCorrectAnswers += 1
        }
    }

}