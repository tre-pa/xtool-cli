package br.xtool.command;

import br.xtool.annotation.CoreCommand;
import br.xtool.command.core.AbstractCommand;
import br.xtool.command.subcommand.CreateRepositoryComponentCommand;
import br.xtool.context.RepositoryContext;
import br.xtool.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

@CoreCommand
@CommandLine.Command(name = "repo", description = "Comando de gerenciamento do repositório xtool",
    subcommands = CreateRepositoryComponentCommand.class)
public class RepositoryCommand extends AbstractCommand {

    @CommandLine.Option(names = "--modules", description = "Lista todos os módulos xtool")
    private boolean listModulesOption;

    @CommandLine.Option(names = "--components", description = "Lista todos os componentes xtool")
    private boolean listComponentsOption;

    @CommandLine.Option(names = "--all", description = "Lista todos os repositórios xtool")
    private boolean listRepositoriesOption;

    @Autowired
    private RepositoryContext repositoryContext;

    @Autowired
    private Console console;

    @Override
    protected void eachOption(String name, Object value) {
        if(name.equals("--components")) printComponentList();
        if(name.equals("--modules")) printModuleList();
        if(name.equals("--all")) printAllRepo();
    }

    public void printComponentList() {
        console.println("%s / total %d", repositoryContext.getWorkingRepository().getName(), repositoryContext.getWorkingRepository().getTotalComponents());
//        repositoryContext.getWorkingRepository().getModules().stream()
//                .flatMap(module -> module.getComponents().stream())
//                .forEach(component -> console.println("@|blue %s|@ -> %s", component.getName(), component.getDescriptor().getComponentDef().getDescription()));
    }

    public void printModuleList() {
        console.println("%s / total %d", repositoryContext.getWorkingRepository().getName(), repositoryContext.getWorkingRepository().getTotalModules());
        repositoryContext.getWorkingRepository().getModules().stream()
                .forEach(module -> console.println("@|blue %s|@ -> %d componentes", module.getName(), module.getComponents().size()));
    }

    public void printAllRepo() {
        console.println("total %d", repositoryContext.getTotalRepositories());
        repositoryContext.getRepositories().stream()
                .forEach(repo -> console.println("@|blue %s|@ -> %d modules", repo.getName(), repo.getTotalModules()));
    }

    public void printCurrentRepo() {
        console.println("%s", repositoryContext.getWorkingRepository().getName());
    }
}
