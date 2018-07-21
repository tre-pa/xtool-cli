package br.xtool.core.aware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.service.WorkspaceService;
import lombok.SneakyThrows;

public class SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	/**
	 * Define a disponibilidade dos comando do grupo Spring Boot.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return this.workspaceService.getWorkingProject().getProjectType().equals(ProjectType.SPRINGBOOT_PROJECT) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}

	@SneakyThrows
	public ESBootProject getProject() {
		throw new UnsupportedOperationException();
	}

}
