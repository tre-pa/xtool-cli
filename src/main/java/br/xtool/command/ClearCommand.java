package br.xtool.command;

import br.xtool.command.core.AbstractCommand;
import br.xtool.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * Comando para limpar a tela do console.
 */
@Component
@Command(name = "clear", description = "Limpa a tela")
public class ClearCommand extends AbstractCommand {

	@Autowired
	private Console console;

    @Override
    public void run() {
		console.clearScreen();
    }

}
