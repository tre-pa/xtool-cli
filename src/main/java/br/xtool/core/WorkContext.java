package br.xtool.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import br.xtool.core.event.ChangeDirectoryEvent;
import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgProject;
import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.representation.ESBootProject;
import br.xtool.core.representation.impl.EDirectoryImpl;
import br.xtool.core.representation.impl.ENgProjectImpl;
import br.xtool.core.representation.impl.ESBootProjectImpl;
import lombok.Getter;

@Component
public class WorkContext implements PromptProvider {

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
	public void changeTo(String newAbsoluteDirectory) {
		this.directory = EDirectoryImpl.of(newAbsoluteDirectory);
		this.springBootProject = null;
		this.angularProject = null;
		this.applicationEventPublisher.publishEvent(new ChangeDirectoryEvent(this.directory));
	}

	public void changeRelativeTo(String newRelativeDirectory) {
		this.changeTo(FilenameUtils.concat(this.directory.getPath(), newRelativeDirectory));
	}

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
	public ESBootProject getSpringBootProject() {
		if (Objects.isNull(this.springBootProject)) {
			this.springBootProject = ESBootProjectImpl.of(this.directory.getPath());
		}
		return this.springBootProject;
	}

	/**
	 * Retorna a representação do projeto Angular
	 * 
	 * @return
	 */
	public Optional<ENgProject> getAngularProject() {
		if (Objects.isNull(this.angularProject)) {
			this.angularProject = ENgProjectImpl.of(this.directory.getPath());
		}
		return this.angularProject;
	}

	@Override
	public AttributedString getPrompt() {
		if (!this.getDirectory().getProjectType().equals(ProjectType.NONE)) {
			// @formatter:off
			return new AttributedStringBuilder()
					.append("xtool@", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW))
					.append(this.directory.getBaseName(), AttributedStyle.BOLD.foreground(AttributedStyle.GREEN))
					.append(" > ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW))
					.toAttributedString();
			// @formatter:on
		}
		// @formatter:off
		return new AttributedString(
				String.format("xtool@%s > ", this.getDirectory().getBaseName()), 
					AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
		// @formatter:on
	}

}
