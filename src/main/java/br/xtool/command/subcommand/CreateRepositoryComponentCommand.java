package br.xtool.command.subcommand;

import br.xtool.command.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.core.Fabricable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "create:component", description = "Comando de criação de novo componente xtool")
public class CreateRepositoryComponentCommand extends AbstractCommand implements Fabricable {

    @Autowired
    private Console console;

    @Override
    public void run() {
        console.println("CreateRepositoryComponentCommand");
    }
}
