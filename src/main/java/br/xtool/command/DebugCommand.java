package br.xtool.command;

import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * Comando para limpar a tela do console.
 */
@Component
@Command(name = "debug", description = "Ativa o nivel debug no console.")
public class DebugCommand extends AbstractCommand {

	@Autowired
	private Console console;

    @Override
    public void run() {
        if(console.getLevel().equals(Console.Level.DEBUG)) {
            console.setLevel(Console.Level.NORMAL);
            console.println("<< Nivel @|cyan DEBUG|@ desativado.");
        } else {
            console.setLevel(Console.Level.DEBUG);
            console.println(">> Nivel @|cyan DEBUG|@ ativado.");
        }
    }

}
