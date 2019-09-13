package br.xtool.command;

import br.xtool.core.AbstractCommand;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;

/**
 * Comando de execução de componentes.
 */
@Component
@Command(name = "exec", description = "Executa um componente")
public class ExecCommand extends AbstractCommand {

	@Override
	public void setup(CommandLine mainCommandLine) {
		CommandSpec execSpec = CommandSpec.forAnnotatedObject(this);
		// @formatter:off
		CommandSpec componentSpec = CommandSpec.create()
				.name("angular")
				.addOption(picocli.CommandLine.Model.OptionSpec.builder("--name")
						.description("Nome do projeto")
						.completionCandidates(Lists.newArrayList("Angular", "SpringBoot", "SpringBoot:Fullstack"))
						.type(String.class)
						.required(false)
						.build())
				.addOption(picocli.CommandLine.Model.OptionSpec.builder("--no-edit")
						.description("Sem edit")
						.arity("0")
						.required(false)
						.build());
		// @formatter:on
		execSpec.addSubcommand("angular", componentSpec);
		mainCommandLine.addSubcommand("exec", execSpec);
	}

	@Override
	public void run() {
		System.out.println("Oi");
	}
	
}
