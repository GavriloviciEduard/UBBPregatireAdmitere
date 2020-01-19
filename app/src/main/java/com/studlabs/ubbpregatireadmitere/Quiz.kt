package com.studlabs.ubbpregatireadmitere

class Quiz(idC: Int, nameC: String, difficultyC: String) {
    var id: Int = idC
    var name: String = nameC
    var difficulty: String = difficultyC
    var questions: MutableList<Question> = ArrayList()

}