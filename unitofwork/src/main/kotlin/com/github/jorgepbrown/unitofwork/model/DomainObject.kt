package com.github.jorgepbrown.unitofwork.model

import com.github.jorgepbrown.unitofwork.UnitOfWork

abstract class DomainObject {
    init {
        markNew()
    }

    fun markNew() {
        UnitOfWork.current.registerNew(this)
    }

    fun markModified() {
        UnitOfWork.current.registerDirty(this)
    }

    fun markDeleted() {
        UnitOfWork.current.registerDeleted(this)
    }
}