package br.xtool.core.aware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.Workspace;
import br.xtool.core.representation.ProjectRepresentation.Type;

public class RegularAware {

	@Autowired
	private Workspace workspace;

	/**
	 * Define a disponibilidade dos comando do grupo Spring Boot.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return this.workspace.getWorkingProject().getProjectType().equals(Type.NONE) ? Availability.available()
				: Availability.unavailable("O commando não é aplicável ao diretório atual.");
	}

}
