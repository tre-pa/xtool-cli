package br.xtool.command.repo;

import br.xtool.context.RepositoryContext;
import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "repo", description = "Comando de gerenciamento do repositório xtool")
public class RepositoryCommand extends AbstractCommand {

    @CommandLine.Option(names = "--modules", description = "Lista todos os módulos xtool")
    private boolean listModulesOption;

    @Autowired
    private RepositoryContext repositoryContext;

    @Autowired
    private Console console;

    @Override
    public void run() {
        if (listModulesOption) {
            printModuleList();
            return;
        }
        console.println(new CommandLine(this).getUsageMessage());
    }

    private void printModuleList() {
        console.println("total %d", repositoryContext.getRepository().getModules().size());
        repositoryContext.getRepository().getModules().stream()
                .forEach(module -> console.println("@|blue %s|@ -> %d componentes", module.getName(), module.getComponents().size()));
    }
}
