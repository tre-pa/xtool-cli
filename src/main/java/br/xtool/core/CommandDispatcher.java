package br.xtool.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe que recebe os parametros do console e executa os comando correspondente.
 */
@Component
public class CommandDispatcher {

    @Autowired
    @Qualifier("commands")
    private Map<String, AbstractCommand> commands;

    @EventListener
    protected void process(CommandLine.ParseResult parseResult) {
        List<String> normalizedCommands = parseResult.asCommandLineList()
                .stream()
                .filter(cmd -> StringUtils.isNotBlank(cmd.getCommandName()))
                .map(CommandLine::getCommandName)
                .collect(Collectors.toList());
        if (normalizedCommands.size() == 1) {
            String commandName = normalizedCommands.get(0);
            commands.get(commandName).run();
        }

    }

}
