package com.github.jorgepbrown.unitofwork.model

import com.github.jorgepbrown.unitofwork.UnitOfWork

abstract class DomainObject {
    init {
        markNew()
    }

    protected fun markNew() {
        UnitOfWork.current.registerNew(this)
    }

    protected fun markModified() {
        UnitOfWork.current.registerDirty(this)
    }

    protected fun markDeleted() {
        UnitOfWork.current.registerDeleted(this)
    }

}