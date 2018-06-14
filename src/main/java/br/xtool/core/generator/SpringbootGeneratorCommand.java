package br.xtool.core.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.PathContext;

public class SpringbootGeneratorCommand extends GeneratorCommand {

	@Autowired
	private PathContext pathCtx;

	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return Files.exists(Paths.get(pathCtx.getWorkingDirectory(), "pom.xml")) ? Availability.available()
				: Availability.unavailable(
						"O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}
}
