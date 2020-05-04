package com.github.jorgepbrown.unitofwork.context

import com.github.jorgepbrown.unitofwork.*
import com.github.jorgepbrown.unitofwork.model.*
import org.postgresql.ds.PGSimpleDataSource
import java.sql.Connection
import javax.sql.DataSource

abstract class AbstractDbContext : DbContext {
    companion object {
        val dataSource: DataSource = PGSimpleDataSource().apply {
            serverNames = arrayOf("localhost")
            portNumbers = intArrayOf(5432)
            databaseName = "database"
            user = "user"
            password = "password"
        }
    }

    override fun <T : DomainObject> getMapper(type: Class<out T>, connection: Connection): DomainObjectMapper<T> {
        return when (type) {
            Student::javaClass -> {
                StudentMapper(connection)
            }
            Clazz::javaClass -> {
                ClassMapper(connection)
            }
            Course::javaClass -> {
                CourseMapper(connection)
            }
            Teacher::javaClass -> {
                TeacherMapper(connection)
            }
            else -> {
                throw IllegalArgumentException("There is not mapper for the specified class: ${type.name}")
            }
        } as DomainObjectMapper<T>
    }

    override fun <T : DomainObject> getMapper(type: Class<out T>): DomainObjectMapper<T> = getMapper(type, connection)

    protected val connection: Connection
        get() = dataSource.connection

    override fun <R> connect(block: (Connection) -> R): R {
        return connection.use(block)
    }
}