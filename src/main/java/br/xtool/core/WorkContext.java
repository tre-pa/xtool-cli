package br.xtool.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.jboss.forge.roaster.model.JavaUnit;
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
import br.xtool.core.representation.ESpringBootProject;
import br.xtool.core.representation.enums.ProjectType;
import br.xtool.core.utils.RoasterUtils;
import lombok.Getter;

@Component
public class WorkContext implements PromptProvider {

	@Getter
	private EDirectory directory;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private Optional<ESpringBootProject> project = Optional.empty();

	/**
	 * Altera o diretório de trabalho.
	 * 
	 * @param newAbsoluteDirectory
	 */
	public void changeTo(String newAbsoluteDirectory) {
		validateDirectory(newAbsoluteDirectory);
		this.directory = new EDirectory(newAbsoluteDirectory);
		this.project = Optional.empty();
		applicationEventPublisher.publishEvent(new ChangeDirectoryEvent(this.directory));
	}

	public void changeRelativeTo(String newRelativeDirectory) {
		String newAbsoluteDirectory = FilenameUtils.concat(this.directory.getPath(), newRelativeDirectory);
		this.changeTo(newAbsoluteDirectory);
	}

	private void validateDirectory(String newDirectory) {
		if (!Files.isDirectory(Paths.get(newDirectory))) {
			throw new IllegalArgumentException(String.format("O diretório %s não existe", newDirectory));
		}
	}

	private void setupHomeDirectory() throws IOException {
		String home = FilenameUtils.concat(System.getProperty("user.home"), "git");
		Files.createDirectories(Paths.get(home));
		this.directory = new EDirectory(home);
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
	public Optional<ESpringBootProject> getProject() {
		if (!project.isPresent()) {
			if (ESpringBootProject.isValidProject(this.directory.getPath())) {
				// @formatter:off
				Set<JavaUnit> javaUnits = this.directory.getAllFiles().stream()
					.filter(file -> file.getName().endsWith(".java"))
					.map(RoasterUtils::getJavaUnit)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toCollection(HashSet::new));
				// @formatter:on
				this.project = Optional.of(new ESpringBootProject(this.directory.getPath(), javaUnits));
			}
		}
		return this.project;
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
