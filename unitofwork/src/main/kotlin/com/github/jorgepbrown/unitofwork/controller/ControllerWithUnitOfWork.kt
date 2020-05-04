package com.github.jorgepbrown.unitofwork.controller

import com.github.jorgepbrown.unitofwork.context.DbContextWithUnitOfWork
import com.github.jorgepbrown.unitofwork.model.Clazz
import com.github.jorgepbrown.unitofwork.model.Teacher
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ControllerWithUnitOfWork {

    @GetMapping("/classes/{classId}")
    fun newTeacherToClass(
        @RequestParam teacherName: String,
        @PathVariable classId: Long
    ) {
        DbContextWithUnitOfWork().use {
            val classMapper = it.getMapper(Clazz::class.java)

            val teacher = Teacher(teacherName)
            val klass = classMapper.find(classId)

            klass.teacher = teacher
        }
    }
}