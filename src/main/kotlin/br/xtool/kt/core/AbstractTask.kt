package br.xtool.kt.core

import br.xtool.core.Console
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractTask {

    @Autowired
    lateinit var console: Console

}