package br.xtool.command;

import br.xtool.core.AbstractCommand;
import br.xtool.core.RepositoryContext;
import br.xtool.representation.repo.RepositoryRepresentation;
import com.google.common.collect.Lists;
import com.sun.jndi.toolkit.ctx.ComponentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

import java.util.List;
import java.util.Map;

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
        repositoryContext.getRepositories()
                .stream()
                .flatMap(repo -> repo.getModules().stream())
                .flatMap(mod -> mod.getComponents().stream())
                .forEach(cmd -> execSpec.addSubcommand(cmd.getName(), cmd.getCommandSpec()));
        mainCommandLine.addSubcommand("exec", execSpec);

    }

    @Override
    public void run() {
        System.out.println("Oi");
    }

}
