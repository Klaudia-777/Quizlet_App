package com.example.quizlet.model

data class Question(val id: String,
                    val question: String,
                    val answers: MutableList<Answer> = mutableListOf())

data class Answer(val id: String,
                  val text: String,
                  val isCorrect: Boolean)

data class StudentResult(val albumNumber: String,
                         val choices: MutableList<StudentChoice> = mutableListOf())

data class StudentChoice(val questionId: String,
                         val answers: MutableList<String> = mutableListOf())
