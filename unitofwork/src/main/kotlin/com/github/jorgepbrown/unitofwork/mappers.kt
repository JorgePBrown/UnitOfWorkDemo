package com.github.jorgepbrown.unitofwork

import com.github.jorgepbrown.unitofwork.model.*
import java.sql.Connection

abstract class DomainObjectMapper<T : DomainObject>(
    protected val connection: Connection
) {
    abstract fun find(id: Long): T
    abstract fun insert(t: T)
    abstract fun update(t: T)
    abstract fun delete(t: T)
}

class StudentMapper(
    connection: Connection
) : DomainObjectMapper<Student>(connection) {
    override fun find(id: Long): Student {
        TODO("Not yet implemented")
    }

    override fun insert(t: Student) {
        TODO("Not yet implemented")
    }

    override fun update(t: Student) {
        TODO("Not yet implemented")
    }

    override fun delete(t: Student) {
        TODO("Not yet implemented")
    }
}

class ClassMapper(
    connection: Connection
) : DomainObjectMapper<Clazz>(connection) {
    override fun find(id: Long): Clazz {
        TODO("Not yet implemented")
    }

    override fun insert(t: Clazz) {
        TODO("Not yet implemented")
    }

    override fun update(t: Clazz) {
        TODO("Not yet implemented")
    }

    override fun delete(t: Clazz) {
        TODO("Not yet implemented")
    }
}

class CourseMapper(
    connection: Connection
) : DomainObjectMapper<Course>(connection) {
    override fun find(id: Long): Course {
        TODO("Not yet implemented")
    }

    override fun insert(t: Course) {
        TODO("Not yet implemented")
    }

    override fun update(t: Course) {
        TODO("Not yet implemented")
    }

    override fun delete(t: Course) {
        TODO("Not yet implemented")
    }
}

class TeacherMapper(
    connection: Connection
) : DomainObjectMapper<Teacher>(connection) {
    override fun find(id: Long): Teacher {
        TODO("Not yet implemented")
    }

    override fun insert(t: Teacher) {
        TODO("Not yet implemented")
    }

    override fun update(t: Teacher) {
        TODO("Not yet implemented")
    }

    override fun delete(t: Teacher) {
        TODO("Not yet implemented")
    }
}