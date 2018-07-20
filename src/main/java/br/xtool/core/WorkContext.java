package br.xtool.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.xtool.core.event.ChangeDirectoryEvent;
import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.representation.impl.EDirectoryImpl;
import br.xtool.core.representation.impl.ESBootProjectImpl;
import lombok.Getter;

@Component
public class WorkContext {

	@Getter
	private EDirectory directory;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private ESBootProject springBootProject;

	private Optional<ENgProject> angularProject;

	/**
	 * Altera o diretório de trabalho.
	 * 
	 * @param newAbsoluteDirectory
	 */
	@Deprecated
	public void changeTo(String newAbsoluteDirectory) {
		this.directory = EDirectoryImpl.of(newAbsoluteDirectory);
		this.springBootProject = null;
		this.angularProject = null;
		this.applicationEventPublisher.publishEvent(new ChangeDirectoryEvent(this.directory));
	}

	@Deprecated
	public void changeRelativeTo(String newRelativeDirectory) {
		this.changeTo(FilenameUtils.concat(this.directory.getPath(), newRelativeDirectory));
	}

	@Deprecated
	private void setupHomeDirectory() throws IOException {
		String home = FilenameUtils.concat(System.getProperty("user.home"), "git");
		Files.createDirectories(Paths.get(home));
		this.directory = EDirectoryImpl.of(home);
	}

	/**
	 * Intercepta a inicialzação do sistema para a definição do diretório padrão de
	 * trabalho.
	 * 
	 * @param contextRefreshedEvent
	 * @throws IOException
	 */
	@EventListener
	protected void onContextRefreshEvent(ContextRefreshedEvent contextRefreshedEvent) throws IOException {
		this.setupHomeDirectory();
	}

	/**
	 * Retorna a representação do projeto Spring Boot
	 * 
	 * @return
	 */
	@Deprecated
	public ESBootProject getSpringBootProject() {
		if (Objects.isNull(this.springBootProject)) {
			this.springBootProject = ESBootProjectImpl.of(this.directory);
		}
		return this.springBootProject;
	}

	/**
	 * Retorna a representação do projeto Angular
	 * 
	 * @return
	 */
	@Deprecated
	public Optional<ENgProject> getAngularProject() {
		//		if (Objects.isNull(this.angularProject)) {
		//			this.angularProject = ENgProjectImpl.of(this.directory);
		//		}
		return this.angularProject;
	}

}
