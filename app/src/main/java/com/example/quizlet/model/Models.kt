package com.example.quizlet.model

import java.util.*

data class Question(
    val id: String,
    val question: String,
    val answers: MutableList<Answer> = mutableListOf()
)

data class Answer(
    val id: String,
    val text: String,
    val correctOrNot: Boolean
)

data class StudentResult(
    val albumNumber: String,
    val choices: MutableList<StudentChoice>,
    val result: String
)

data class StudentChoice(
    val questionId: String,
    val answers: MutableList<String>
)

