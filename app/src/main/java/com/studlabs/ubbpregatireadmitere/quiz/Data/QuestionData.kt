package com.studlabs.ubbpregatireadmitere.quiz.Data

data class QuestionData(var id: Int, var content: String, var answers: MutableList<AnswerData>)