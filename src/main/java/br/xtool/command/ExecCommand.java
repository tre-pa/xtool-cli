package br.xtool.command;

import br.xtool.annotation.CoreCommand;
import br.xtool.command.core.AbstractCommand;
import br.xtool.context.RepositoryContext;
import br.xtool.context.WorkspaceContext;
import br.xtool.core.Console;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

/**
 * Comando de execução de componentes.
 */
@CoreCommand
@Command(name = "exec", description = "Executa um componente Xtool")
public class ExecCommand extends AbstractCommand {

    @Autowired
    private WorkspaceContext workspaceContext;

    @Autowired
    private RepositoryContext repositoryContext;

    @Autowired
    private Console console;

//    @Autowired
//    private ComponentExecutor componentExecutor;

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
//        repositoryContext.getWorkingRepository().getModules()
//                .stream()
//                .flatMap(modules -> modules.getComponents().stream())
//                .map(ComponentRepresentation::getComponentDescriptor)
//                .forEach(descriptor -> execSpec.addSubcommand(descriptor.getName(), repositoryContext.create(descriptor)));
    }

    @Override
    public void run() {
//        if (getParseResult().subcommand().hasSubcommand()) {
//            if (!getParseResult().subcommand().subcommand().isUsageHelpRequested()) {
//                String subcommandName = getParseResult().subcommand().subcommand().commandSpec().name();
//                Optional<ComponentRepresentation> component = repositoryContext.findComponentByName(subcommandName);
//                if(component.isPresent()) {
//                    ComponentExecutionContext ctx = ComponentExecutionContext.of(component.get(), workspaceContext.getWorkingProject(), getParseResult());
//                    ComponentRepresentation cmd = component.get();
//                    if(isEnabled(cmd.getComponentDescriptor(), ctx)) {
//                        componentExecutor.run(cmd, ctx);
//                    }
//
//                }
//            }
//        }
    }

//    private boolean isEnabled(ComponentDescriptorRepresentation componentDescriptor, ComponentExecutionContext ctx) {
//        if(Objects.nonNull(componentDescriptor.getEnabled())) {
//            ComponentDescriptorRepresentation.ComponentDescriptorEnabledRepresentation enabled = componentDescriptor.getEnabled();
//            if(StringUtils.isNotBlank(enabled.getWhen())) {
//                boolean isEnabled = ctx.parseAsBoolean(enabled.getWhen());
//                if(!isEnabled) {
//                    console.println("@|red,bold "+componentDescriptor.getEnabled().onFail()+"|@");
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

}
