package com.studlabs.ubbpregatireadmitere.quiz.Data

data class QuizData(var id: Int, var name: String, var difficulty: String, var questions: MutableList<QuestionData>)