package br.xtool.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe que recebe os parametros do console e executa os comando correspondente.
 */
@Component
public class CommandDispatcher {

    @Autowired
    private Console console;

    @Autowired
    @Qualifier("commands")
    private Map<String, AbstractCommand> commandList;

    @EventListener
    protected void process(CommandLine.ParseResult parseResult) {
        ArrayDeque<String> commands = parseResult.asCommandLineList()
                .stream()
                .map(CommandLine::getCommandName)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toCollection(ArrayDeque::new));
        CommandLine.printHelpIfRequested(parseResult);
//        if (commands.size() == 1) {
//            console.debug("@|magenta CommandDispatcher.process(command=%s) %b|@", commands, parseResult.);
//            String commandName = commands.poll();
//            commandList.get(commandName).run();
//        } else if (commands.size() == 2) {
//            console.debug("@|magenta CommandDispatcher.process(command=%s, args=%s) |@", commands, parseResult.subcommand().matchedArgs());
//        }
    }

}
