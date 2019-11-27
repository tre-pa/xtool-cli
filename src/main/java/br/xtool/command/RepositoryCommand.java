package br.xtool.command;

import br.xtool.annotation.OptionFn;
import br.xtool.command.subcommand.CreateRepositoryComponentCommand;
import br.xtool.context.RepositoryContext;
import br.xtool.command.core.AbstractCommand;
import br.xtool.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "repo", description = "Comando de gerenciamento do repositório xtool",
    subcommands = CreateRepositoryComponentCommand.class)
public class RepositoryCommand extends AbstractCommand {

    @CommandLine.Option(names = "--modules", description = "Lista todos os módulos xtool")
    private boolean listModulesOption;

    @CommandLine.Option(names = "--components", description = "Lista todos os componentes xtool")
    private boolean listComponentsOption;

    @CommandLine.Option(names = "--list", description = "Lista todos os repositórios xtool")
    private boolean listRepositoriesOption;

    @Autowired
    private RepositoryContext repositoryContext;

    @Autowired
    private Console console;

    @OptionFn("--components")
    public void printComponentList() {
        console.println("%s / total %d", repositoryContext.getWorkingRepository().getName(), repositoryContext.getWorkingRepository().getModules().stream()
                .flatMap(module -> module.getComponents().stream())
                .count());
        repositoryContext.getWorkingRepository().getModules().stream()
                .flatMap(module -> module.getComponents().stream())
                .forEach(component -> console.println("@|blue %s|@ -> %s", component.getName(), component.getDescriptor().getComponentDef().getDescription()));
        return;
    }

    @OptionFn("--modules")
    public void printModuleList() {
        console.println("%s / total %d", repositoryContext.getWorkingRepository().getName(), repositoryContext.getWorkingRepository().getModules().size());
        repositoryContext.getWorkingRepository().getModules().stream()
                .forEach(module -> console.println("@|blue %s|@ -> %d componentes", module.getName(), module.getComponents().size()));
    }

    @OptionFn("--list")
    public void printRepoList() {
        console.println("total %d", repositoryContext.getRepositories().size());
        repositoryContext.getRepositories().stream()
                .forEach(repo -> console.println("@|blue %s|@ -> %d modules", repo.getName(), repo.getModules().size()));
    }

    public void printCurrentRepo() {
        console.println("%s", repositoryContext.getWorkingRepository().getName());
    }
}
