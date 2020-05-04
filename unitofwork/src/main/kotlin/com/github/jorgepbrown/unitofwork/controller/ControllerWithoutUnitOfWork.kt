package com.github.jorgepbrown.unitofwork.controller

import com.github.jorgepbrown.unitofwork.context.DbContextWithoutUnitOfWork
import com.github.jorgepbrown.unitofwork.model.Clazz
import com.github.jorgepbrown.unitofwork.model.Teacher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

class ControllerWithoutUnitOfWork {

    @GetMapping("/classes/{classId}")
    fun newTeacherToClass(
        @RequestParam teacherName: String,
        @PathVariable classId: Long
    ) {
        val context = DbContextWithoutUnitOfWork()

        context.connect {
            val teacherMapper = context.getMapper(Teacher::class.java, it)
            val classMapper = context.getMapper(Clazz::class.java, it)

            val teacher = Teacher(teacherName)
            val clazz = classMapper.find(classId)

            clazz.teacher = teacher

            teacherMapper.insert(teacher)
            classMapper.update(clazz)
        }
    }
}