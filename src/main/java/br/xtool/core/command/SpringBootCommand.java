package br.xtool.core.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.PathService;
import br.xtool.core.representation.SpringBootProjectRepresentation;

public class SpringBootCommand extends XCommand {

	@Autowired
	private PathService pathCtx;
	
	private SpringBootProjectRepresentation springBootProject;
	
	/**
	 * Define a disponibilidade dos comando do grupo Spring Boot.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return SpringBootProjectRepresentation.isValidSpringBootProject(pathCtx.getWorkingDirectory()) ? Availability.available()
				: Availability.unavailable(
						"O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}

	/**
	 * Retorna o modelo do projeto Spring Boot do diretório de trabalho atual.
	 * 
	 * @return
	 * @throws IOException
	 */
	protected SpringBootProjectRepresentation getProject() throws IOException {
		if(this.springBootProject == null) {
			this.springBootProject = pathCtx.getSpringBootProject().get();
		}
		return this.springBootProject;
	}
}
