package com.example.quizlet.model

object ContextHolder {
    lateinit var albumNumber: String
    lateinit var testId: String
    lateinit var questions: List<Question>
    lateinit var  studentResult: StudentResult
}