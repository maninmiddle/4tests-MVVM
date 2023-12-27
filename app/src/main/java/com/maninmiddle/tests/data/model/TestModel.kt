package com.maninmiddle.tests.data.model

import kotlinx.serialization.Serializable

data class TestModel(
    var name: String ,
    var subject: String,
    var completeTime: Int,
    var password: String,
    var tasks: MutableList<TaskModel>
) {
    val id: Int = -1
    var isAnimated: Boolean = false
}

fun TestModel.isDefault(): Boolean {
    return name.isEmpty() && subject.isEmpty() && completeTime == 0 && password.isEmpty() && tasks.isEmpty()
}