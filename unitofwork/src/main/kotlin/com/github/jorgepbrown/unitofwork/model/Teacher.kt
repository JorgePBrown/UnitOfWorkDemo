package com.github.jorgepbrown.unitofwork.model

class Teacher(
    name: String
) : DomainObject() {
    var name: String = name
        set(value) {
            markModified()
            field = value
        }
}