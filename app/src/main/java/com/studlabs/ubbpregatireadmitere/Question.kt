package com.studlabs.ubbpregatireadmitere

class Question(idC: Int, contentC: String) {
    var id: Int = idC
    var content: String = contentC
    var answers: MutableList<Answer> = ArrayList()

}