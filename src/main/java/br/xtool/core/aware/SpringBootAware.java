package br.xtool.core.aware;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.WorkContext;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EProject.ProjectType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public EBootProject getProject() {
		return this.workContext.getSpringBootProject().get();
	}

}
