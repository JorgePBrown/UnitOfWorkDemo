package com.github.jorgepbrown.unitofwork.model

class Clazz(
    private val students: MutableList<Student> = mutableListOf(),
    teacher: Teacher
) : DomainObject() {

    var teacher: Teacher = teacher
        set(value) {
            markModified()
            field = value
        }

    fun addStudent(student: Student) {
        markModified()
        students.add(student)
    }
}