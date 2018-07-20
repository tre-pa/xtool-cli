package br.xtool.core.aware;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.representation.ESBootProject;
import lombok.SneakyThrows;

@Deprecated
public class SpringBootAware {

	@Autowired
	private WorkContext workContext;

	@PostConstruct
	protected void init() throws FileSystemException {
	}

	/**
	 * Define a disponibilidade dos comando do grupo Spring Boot.
	 * 
	 * @return
	 * @throws IOException
	 */
	@ShellMethodAvailability
	public Availability availabilitySpringBootCommand() throws IOException {
		return Stream.of(ProjectType.SPRINGBOOT1_PROJECT, ProjectType.SPRINGBOOT2_PROJECT).anyMatch(p -> p.equals(this.workContext.getDirectory().getProjectType())) ? Availability.available()
				: Availability.unavailable("O diretório de trabalho não é um projeto maven válido. Use o comando cd para alterar o diretório de trabalho.");
	}

	@SneakyThrows
	public ESBootProject getProject() {
		return this.workContext.getSpringBootProject();
	}

}
