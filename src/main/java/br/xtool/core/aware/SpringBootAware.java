package br.xtool.core.aware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.representation.EProject;
import br.xtool.core.service.WorkspaceService;

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
		return this.workspaceService.getWorkingProject().getProjectType().equals(EProject.Type.SPRINGBOOT) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}

}
