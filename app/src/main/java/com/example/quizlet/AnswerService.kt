package com.example.quizlet

import com.example.quizlet.model.Answer
import com.example.quizlet.model.Question

class AnswerService(val allTheQuestions: List<Question>) {


    private fun getAllTheCorrectAnswersFromQuestions(questionId: String): MutableList<Answer> {
        val correct = allTheQuestions.filter { it.id==questionId }.flatMap { it.answers }
        val correctAnswers = correct.map { it }.filter { it.correctOrNot }.toMutableList()
        return correctAnswers
    }

    private fun getAnswerIdForAllTheAnswers(questionId: String): List<String> {
        val answerObjects = getAllTheCorrectAnswersFromQuestions(questionId)
        return answerObjects.map { it.id }
    }

    fun getPointsFromAnswers(ids: List<String>, questionId: String): Int {
        val allAnswerIds = getAnswerIdForAllTheAnswers(questionId)
        return if (ids == allAnswerIds) 1 else 0
    }

}