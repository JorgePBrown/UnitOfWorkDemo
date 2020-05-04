package com.github.jorgepbrown.unitofwork

import com.github.jorgepbrown.unitofwork.context.DbContext
import com.github.jorgepbrown.unitofwork.model.Clazz
import com.github.jorgepbrown.unitofwork.model.Course
import com.github.jorgepbrown.unitofwork.model.DomainObject
import com.github.jorgepbrown.unitofwork.model.Student

private val INSERTION_ORDER = listOf(
    Course::class.java,
    Clazz::class.java,
    Student::class.java
)

class UnitOfWork {
    companion object {
        private val units: ThreadLocal<UnitOfWork> = ThreadLocal()
        fun new() {
            units.set(UnitOfWork())
        }

        fun remove() {
            units.set(null) // Remove reference so garbage collector can collect it
        }

        val current: UnitOfWork
            get() = units.get()
    }

    private val created = mutableListOf<DomainObject>()
    private val modified = mutableListOf<DomainObject>()
    private val deleted = mutableListOf<DomainObject>()

    fun registerNew(domainObject: DomainObject) {
        if (!created.contains(domainObject)) throw IllegalStateException("Creating an already created domain object.")
        if (!modified.contains(domainObject)) throw IllegalStateException("Creating an already existent and modified domain object.")
        if (!deleted.contains(domainObject)) throw IllegalStateException("Creating a domain object that has been deleted.")

        created.add(domainObject)
    }

    fun registerDirty(domainObject: DomainObject) {
        if (!deleted.contains(domainObject)) throw IllegalStateException("Modifying a domain object that has been deleted.")
        if (!created.contains(domainObject) && !modified.contains(domainObject))
            created.add(domainObject)
    }

    fun registerDeleted(domainObject: DomainObject) {
        if (created.remove(domainObject)) return

        modified.remove(domainObject)
        if (!deleted.contains(domainObject))
            deleted.add(domainObject)
    }

    fun commit(dbContext: DbContext) {
        dbContext.connect { connection ->
            val getMapper: (Class<out DomainObject>) -> DomainObjectMapper<DomainObject> = {
                dbContext.getMapper(it, connection)
            }
            insert(getMapper)
            update(getMapper)
            delete(getMapper)
        }
    }

    fun insert(getMapper: (Class<out DomainObject>) -> DomainObjectMapper<in DomainObject>) {
        work(
            getMapper,
            created,
            INSERTION_ORDER,
            DomainObjectMapper<DomainObject>::insert
        )
    }

    fun update(getMapper: (Class<out DomainObject>) -> DomainObjectMapper<DomainObject>) {
        work(
            getMapper,
            modified,
            INSERTION_ORDER,
            DomainObjectMapper<DomainObject>::update
        )
    }

    fun delete(getMapper: (Class<out DomainObject>) -> DomainObjectMapper<DomainObject>) {
        work(
            getMapper,
            deleted,
            INSERTION_ORDER.reversed(),
            DomainObjectMapper<DomainObject>::delete
        )
    }

    private fun work(
        getMapper: (Class<out DomainObject>) -> DomainObjectMapper<in DomainObject>,
        objects: List<DomainObject>,
        order: List<Class<out DomainObject>>,
        map: DomainObjectMapper<DomainObject>.(DomainObject) -> Unit
    ) {
        val groupedByClassObjects = objects.groupBy {
            it::class.java
        }

        order.forEach { type ->
            val objectsOfSameType = groupedByClassObjects[type]

            objectsOfSameType?.forEach {
                getMapper(type).map(it)
            }
        }
    }
}