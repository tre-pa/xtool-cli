package br.xtool.command;

import br.xtool.annotation.CoreCommand;
import br.xtool.command.core.AbstractCommand;
import br.xtool.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.Command;

/**
 * Comando para limpar a tela do console.
 */
@CoreCommand
@Command(name = "clear", description = "Limpa a tela")
public class ClearCommand extends AbstractCommand {

	@Autowired
	private Console console;

    @Override
    public void run() {
		console.clearScreen();
    }

}
