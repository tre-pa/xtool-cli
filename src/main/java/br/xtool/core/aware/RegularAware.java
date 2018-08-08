package br.xtool.core.aware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.representation.EProject.Type;
import br.xtool.service.WorkspaceService;

public class RegularAware {

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
		return this.workspaceService.getWorkingProject().getProjectType().equals(Type.NONE) ? Availability.available()
				: Availability.unavailable("O commando não é aplicável ao diretório atual.");
	}

}
