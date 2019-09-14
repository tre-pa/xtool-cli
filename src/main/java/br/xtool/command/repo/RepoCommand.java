package br.xtool.command.repo;

import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.core.RepositoryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "repo")
public class RepoCommand extends AbstractCommand {

    @Autowired
    private RepositoryContext repositoryContext;

    @Autowired
    private Console console;

    @CommandLine.Option(names = {"--list"}, required = false, description = "Lista os repositórios de componentes.")
    private boolean listOptions;

    @Override
    public void run() {

    }
}
