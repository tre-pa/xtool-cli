package br.xtool.core.command;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.jline.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

import br.xtool.core.WorkContext;
import br.xtool.core.event.ChangeDirectoryEvent;
import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringBootCommand {

	@Autowired
	private WorkContext workContext;

	private ESpringBootProject project;

	private final FileAlterationMonitor monitor = new FileAlterationMonitor();

	private FileAlterationObserver fao;

	private boolean monitorRunning = false;

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
	public ESpringBootProject getProject() {
		if (Objects.isNull(this.project)) {
			this.project = this.workContext.getSpringBootProject().get();
			//			this.fao = new FileAlterationObserver(this.project.getMainDir());
			//			this.fao.addListener(this.project);
			//			this.monitor.addObserver(this.fao);
			//			this.monitor.start();
			//			this.monitorRunning = true;
			//			Log.info("Registrando observers do projeto ", this.getProject().getName());
			//			log.info("Observers iniciando para projeto ", this.project.getName());
		}
		return this.project;
	}

	@EventListener
	@SneakyThrows
	protected void onChangeDirectory(ChangeDirectoryEvent evt) {
		if (this.monitorRunning) {
			Log.info("Parando observers do projeto ", this.getProject().getName());
			log.info("Parando observers para projeto {}", this.project.getName());
			this.monitor.removeObserver(this.fao);
			this.monitor.stop();
			this.monitorRunning = false;
			log.info("Observers parados para projeto {}", this.project.getName());
			this.project = null;
		}
	}

}
