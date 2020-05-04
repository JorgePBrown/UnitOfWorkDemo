package com.github.jorgepbrown.unitofwork

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UnitOfWorkApplication

fun main(args: Array<String>) {
    runApplication<UnitOfWorkApplication>(*args)
}
