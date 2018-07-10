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

	//	private EProject project;

	private Optional<ESpringBootProject> springBootProject;

	/**
	 * Altera o diretório de trabalho.
	 * 
	 * @param newAbsoluteDirectory
	 */
	public void changeTo(String newAbsoluteDirectory) {
		this.directory = EDirectory.of(newAbsoluteDirectory);
		this.springBootProject = null;
		applicationEventPublisher.publishEvent(new ChangeDirectoryEvent(this.directory));
	}

	public void changeRelativeTo(String newRelativeDirectory) {
		String newAbsoluteDirectory = FilenameUtils.concat(this.directory.getPath(), newRelativeDirectory);
		this.changeTo(newAbsoluteDirectory);
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
		if (Objects.isNull(this.springBootProject)) {
			this.springBootProject = ESpringBootProject.of(this.directory.getPath());
		}
		return this.springBootProject;
	}

	/**
	 * Retorna a representação do projeto Angular
	 * 
	 * @return
	 */
	public Optional<EAngularProject> getAngularProject() {
		//			if (Objects.isNull(this.project)) {
		//				if (Stream.of(ProjectType.ANGULAR5_PROJECT, ProjectType.ANGULAR6_PROJECT).anyMatch(p -> p.equals(this.getDirectory().getProjectType()))) {
//				// @formatter:off
//				SortedSet<ENgClass> ngClasses = this.directory.getAllFiles().stream()
//					.filter(file -> Arrays.asList(".module.ts", ".component.ts", ".service.ts").stream().anyMatch(p -> file.getPath().endsWith(p)))
//					.map(ENgClass::new)
//					.collect(Collectors.toCollection(TreeSet::new));
//				// @formatter:on
		//					this.project = new EAngularProject(this.directory.getPath(), ngClasses);
		//				}
		//			}
		//			return Optional.of((EAngularProject) this.project);
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
