package br.xtool.command;

import br.xtool.context.RepositoryContext;
import br.xtool.context.WorkspaceContext;
import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.core.DescriptorContext;
import br.xtool.kt.core.ComponentExecutor;
import br.xtool.representation.repo.ComponentRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Comando de execução de componentes.
 */
@Component
@Command(name = "exec", description = "Executa um componente Xtool")
public class ExecCommand extends AbstractCommand {

    @Autowired
    private WorkspaceContext workspaceContext;

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
            String subcommandName = getParseResult().subcommand().subcommand().commandSpec().name();
            Optional<ComponentRepresentation> component = repositoryContext.findComponentByName(subcommandName);
            component.ifPresent(comp -> componentExecutor.run(
                    comp,
                    createDescriptorContext(comp,getParseResult())));
            return;
        }
        console.println(new CommandLine(this).getUsageMessage());
    }

    public DescriptorContext createDescriptorContext(ComponentRepresentation component, CommandLine.ParseResult parseResult) {
        Map<String, Object> params = parseResult.subcommand().subcommand().matchedOptions()
                .stream()
                .collect(Collectors.toMap(
                        op -> component.getDescriptor().getDef().findParamByLabel(op.names()[0]).getId(),
                        op -> op.getValue()));
        return new DescriptorContext(params);
    }

}
