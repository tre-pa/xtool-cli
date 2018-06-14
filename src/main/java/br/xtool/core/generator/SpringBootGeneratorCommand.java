package br.xtool.core.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.PathContext;
import br.xtool.core.model.SpringBootProject;

public class SpringBootGeneratorCommand extends GeneratorCommand {

	@Autowired
	private PathContext pathCtx;

	/**
	 * Define a disponibilidade dos comando do grupo Spring Boot.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return SpringBootProject.isValidSpringBootProject(pathCtx.getWorkingDirectory()) ? Availability.available()
				: Availability.unavailable(
						"O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}

	protected SpringBootProject getProject() throws IOException {
		return pathCtx.getSpringBootProject().get();
	}
}
