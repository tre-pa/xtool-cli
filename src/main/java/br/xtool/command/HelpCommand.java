package br.xtool.command;

import br.xtool.annotation.CoreCommand;
import br.xtool.command.core.AbstractCommand;
import br.xtool.xtoolcore.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.Command;

/**
 * Comando para limpar a tela do console.
 */
@CoreCommand
@Command(name = "help", description = "Ajuda da aplicação")
public class HelpCommand extends AbstractCommand {

	@Autowired
	private Console console;

    @Override
    public void run() {
        getCommandList().entrySet().stream()
                .filter(e -> e.getValue().getClass().isAnnotationPresent(CoreCommand.class))
                .map(e -> e.getValue().getClass().getAnnotation(Command.class))
                .filter(e -> !e.name().equals("help"))
                .forEach(e -> console.println("@|blue %s|@ -- %s", e.name(), e.description()[0]));
    }

}
