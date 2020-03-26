package br.xtool.kt.component

import br.xtool.xtoolcore.core.AbstractXtoolComponent
import br.xtool.xtoolcore.service.Console
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import picocli.CommandLine

/**
 * Component Xtool que imprime o hello World no console.
 */
@Component
@CommandLine.Command(name = "hello:world", description = ["Comando que imprime o Hello World!"], mixinStandardHelpOptions = true, version = ["1.0"])
class HelloWorldComponent : AbstractXtoolComponent() {

    @Autowired
    lateinit var console: Console

    override fun run() {
        console.println("Hello World!")
    }
}