package br.xtool.picocli;

import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.ArrayDeque;
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
        console.debug("@|magenta CommandDispatcher.process(command=%s, args=%s)|@", commands, parseResult.subcommand().matchedOptions());

        AbstractCommand command = commandList.get(commands.getFirst());
        command.setParseResult(parseResult);
        command.run();
    }

}
