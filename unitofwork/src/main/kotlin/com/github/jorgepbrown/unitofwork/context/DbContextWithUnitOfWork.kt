package com.github.jorgepbrown.unitofwork.context

import com.github.jorgepbrown.unitofwork.UnitOfWork
import java.io.Closeable

class DbContextWithUnitOfWork : AbstractDbContext(), Closeable {

    init {
        UnitOfWork.new()
    }

    fun commit() {
        UnitOfWork.current.commit(this)
    }

    override fun close() {
        UnitOfWork.remove()
    }
}