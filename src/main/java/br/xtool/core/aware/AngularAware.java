package br.xtool.core.aware;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject.ProjectType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AngularAware {

	@Autowired
	private WorkContext workContext;

	/**
	 * Define a disponibilidade dos comando do grupo Angular.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return Stream.of(ProjectType.ANGULAR5_PROJECT, ProjectType.ANGULAR6_PROJECT).anyMatch(p -> p.equals(this.workContext.getDirectory().getProjectType())) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto angular válido. Use o comando cd para alterar o diretório de trabalho.");
	}

	@SneakyThrows
	public ENgProject getProject() {
		return this.workContext.getAngularProject().get();
	}
}
