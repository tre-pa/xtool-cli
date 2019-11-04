package br.xtool.command;

import br.xtool.core.AbstractCommand;
import br.xtool.core.Console;
import br.xtool.core.RepositoryContext;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.directive.XDescriptorRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

/**
 * Comando de execução de componentes.
 */
@Component
@Command(name = "exec", description = "Executa um componente Xtool", mixinStandardHelpOptions = true)
public class ExecCommand extends AbstractCommand {

    @Autowired
    private RepositoryContext repositoryContext;

    @Autowired
    private Console console;

    @Override
    public void setup(CommandLine mainCommandLine) {
        CommandSpec execSpec = CommandSpec.forAnnotatedObject(this);
        //addComponentCommands(execSpec);
        mainCommandLine.addSubcommand("exec", execSpec);

        //		// @formatter:off
//		CommandSpec componentSpec = CommandSpec.create()
//				.name("angular")
//				.addOption(picocli.CommandLine.Model.OptionSpec.builder("--name")
//						.description("Nome do projeto")
//						.completionCandidates(Lists.newArrayList("Angular", "SpringBoot", "SpringBoot:Fullstack"))
//						.type(String.class)
//						.required(false)
//						.build())
//				.addOption(picocli.CommandLine.Model.OptionSpec.builder("--no-edit")
//						.description("Sem edit")
//						.arity("0")
//						.required(false)
//						.build());
//		// @formatter:on
//		commandSpecs.forEach((k,v) -> execSpec.addSubcommand(k, v));

    }

    /**
     * Adiciona os comandos provenientes do componentes xtool.
     *
     * @param execSpec
     */
    private void addComponentCommands(CommandSpec execSpec) {
        repositoryContext.getRepository().getModules()
                .stream()
                .flatMap(modules -> modules.getComponents().stream())
                .map(ComponentRepresentation::getDescriptor)
                .map(XDescriptorRepresentation::getXComponent)
                .forEach(cmd -> execSpec.addSubcommand(cmd.getDescriptor().getComponent().getName(), cmd.getCommandSpec()));
    }

    @Override
    public void run() {
//        console.println("Oi");
    }

}
