package com.github.jorgepbrown.unitofwork.context

import com.github.jorgepbrown.unitofwork.DomainObjectMapper
import com.github.jorgepbrown.unitofwork.model.DomainObject
import java.sql.Connection

interface DbContext {
    fun <T : DomainObject> getMapper(type: Class<out T>, connection: Connection): DomainObjectMapper<T>
    fun <T : DomainObject> getMapper(type: Class<out T>): DomainObjectMapper<T>

    fun <T> connect(block: (Connection) -> T): T
}