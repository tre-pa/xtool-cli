package br.xtool.command;

import br.xtool.core.AbstractCommand;
import br.xtool.core.RepositoryContext;
import br.xtool.representation.repo.ComponentRepresentation;
import br.xtool.representation.repo.DescriptorRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

/**
 * Comando de execução de componentes.
 */
@Component
@Command(name = "exec", description = "Executa um componente Xtool")
public class ExecCommand extends AbstractCommand {

    @Autowired
    private RepositoryContext repositoryContext;

    @Override
    public void setup(CommandLine mainCommandLine) {
        CommandSpec execSpec = CommandSpec.forAnnotatedObject(this);
        repositoryContext.getRepositories()
                .stream()
                .flatMap(repo -> repo.getModules().stream())
                .flatMap(modules -> modules.getComponents().stream())
                .map(ComponentRepresentation::getDescriptor)
                .map(DescriptorRepresentation::getComponentDirective)
                .forEach(cmd -> execSpec.addSubcommand(cmd.getDescriptor().getComponent().getName(), cmd.getCommandSpec()));
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

    @Override
    public void run() {
        System.out.println("Oi");
    }

}
