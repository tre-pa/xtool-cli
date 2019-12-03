package br.xtool.command.core;


import br.xtool.core.Console;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

import java.util.Map;

/**
 * Classe base para todos os comandos Xtool.
 */
public abstract class AbstractCommand implements Runnable {

    @Setter
    @Getter
    private CommandLine.ParseResult parseResult;

    @Autowired
    private Console console;

    @Setter
    @Getter
    private Map<String, AbstractCommand> commandList;

    @CommandLine.Option(names = "--help", description = "Ajuda do comando", usageHelp = true)
    private boolean help;

    /**
     * Envia o mainCommandLine para inclusao de subcomandos.
     * @param mainCommandLine
     */
    public void setup(CommandLine mainCommandLine) {
        String commandName = this.getClass().getAnnotation(CommandLine.Command.class).name();
        mainCommandLine.addSubcommand(commandName, this);
    }

    protected void execSubcommands() {
        String commandName = parseResult.subcommand().subcommand().commandSpec().name();
        if(commandList.containsKey(commandName)) {
            commandList.get(commandName).run();
        }
    }

    protected void eachOption(String name, Object value) {

    }

    @Override
    public void run() {
        if(parseResult.subcommand().hasSubcommand()) {
            execSubcommands();
            return;
        }
        getParseResult().subcommand().matchedOptions().forEach(op -> this.eachOption(op.names()[0], op.getValue()));
    }
}
