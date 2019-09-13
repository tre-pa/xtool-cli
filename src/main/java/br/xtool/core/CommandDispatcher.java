package br.xtool.core;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Classe que recebe os parametros do console e executa os comando correspondente.
 */
@Component
public class CommandDispatcher {

    @EventListener
    protected void process(CommandLine.ParseResult parseResult) {
    }

}
