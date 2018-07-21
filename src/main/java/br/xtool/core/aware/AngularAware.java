package br.xtool.core.aware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.service.WorkspaceService;
import lombok.SneakyThrows;

public class AngularAware {

	@Autowired
	private WorkspaceService workspaceService;

	/**
	 * Define a disponibilidade dos comando do grupo Angular.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return this.workspaceService.getWorkingDirectory().getProjectType().equals(ProjectType.ANGULAR_PROJECT) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto angular válido. Use o comando cd para alterar o diretório de trabalho.");
	}

	@SneakyThrows
	public ENgProject getProject() {
		throw new UnsupportedOperationException();
	}
}
