package com.github.jorgepbrown.unitofwork.context

import com.github.jorgepbrown.unitofwork.UnitOfWork
import java.io.Closeable

class DbContextWithUnitOfWork : AbstractDbContext(), Closeable {

    init {
        UnitOfWork.new()
    }

    override fun close() {
        UnitOfWork.current.commit(this)
        UnitOfWork.remove()
    }
}