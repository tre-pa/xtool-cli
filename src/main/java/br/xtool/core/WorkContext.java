package br.xtool.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.angular.EAngularProject;
import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

@Component
public class WorkContext implements PromptProvider {

	@Getter
	private EDirectory directory;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * Altera o diretório de trabalho.
	 * 
	 * @param newAbsoluteDirectory
	 */
	public void changeTo(String newAbsoluteDirectory) {
		this.directory = EDirectory.of(newAbsoluteDirectory);
		//		applicationEventPublisher.publishEvent(new ChangeDirectoryEvent(this.directory));
	}

	public void changeRelativeTo(String newRelativeDirectory) {
		this.changeTo(FilenameUtils.concat(this.directory.getPath(), newRelativeDirectory));
	}

	private void setupHomeDirectory() throws IOException {
		String home = FilenameUtils.concat(System.getProperty("user.home"), "git");
		Files.createDirectories(Paths.get(home));
		this.directory = EDirectory.of(home);
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
	public Optional<ESpringBootProject> getSpringBootProject() {
		return ESpringBootProject.of(this.directory.getPath());
	}

	/**
	 * Retorna a representação do projeto Angular
	 * 
	 * @return
	 */
	public Optional<EAngularProject> getAngularProject() {
		return Optional.empty();
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
