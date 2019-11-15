package br.xtool.command;

import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.context.RepositoryContext;
import br.xtool.kt.core.ComponentExecutor;
import br.xtool.representation.repo.ComponentRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

import java.util.Optional;

/**
 * Comando de execução de componentes.
 */
@Component
@Command(name = "exec", description = "Executa um componente Xtool")
public class ExecCommand extends AbstractCommand {

    @Autowired
    private RepositoryContext repositoryContext;

    @Autowired
    private Console console;

    @Autowired
    private ComponentExecutor componentExecutor;

    @Override
    public void setup(CommandLine mainCommandLine) {
        CommandSpec execSpec = CommandSpec.forAnnotatedObject(this);
        addComponentCommands(execSpec);
        mainCommandLine.addSubcommand("exec", execSpec);
    }

    /**
     * Adiciona os comandos provenientes dos componentes xtool.
     *
     * @param execSpec
     */
    private void addComponentCommands(CommandSpec execSpec) {
        repositoryContext.getRepository().getModules()
                .stream()
                .flatMap(modules -> modules.getComponents().stream())
                .map(ComponentRepresentation::getDescriptor)
                .forEach(descriptor -> execSpec.addSubcommand(descriptor.getComponent().getName(), repositoryContext.create(descriptor)));
    }

    @Override
    public void run() {
        if (getParseResult().subcommand().hasSubcommand()) {
            String subcommand = getParseResult().subcommand().subcommand().commandSpec().name();
            Optional<ComponentRepresentation> component = repositoryContext.findComponentByName(subcommand);
            component.ifPresent(componentExecutor::run);
        }
    }

}
