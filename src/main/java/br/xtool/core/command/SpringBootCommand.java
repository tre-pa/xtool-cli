package br.xtool.core.command;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;

public class SpringBootCommand {

	@Autowired
	private WorkContext workContext;

	/**
	 * Define a disponibilidade dos comando do grupo Spring Boot.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return Stream.of(ProjectType.SPRINGBOOT1_PROJECT, ProjectType.SPRINGBOOT2_PROJECT).anyMatch(p -> p.equals(workContext.getDirectory().getProjectType())) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}

	public ESpringBootProject getProject() {
		return workContext.getSpringBootProject().get();
	}

}
