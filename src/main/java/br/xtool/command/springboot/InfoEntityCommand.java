package br.xtool.command.springboot;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaAttribute;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.service.WorkspaceService;

@ShellComponent
public class InfoEntityCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@ShellMethod(key = "info:entity", value = "Exibe informações sobre as entidades JPA do projeto", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		infoAllEntities(bootProject);
	}

	private void infoAllEntities(EBootProject bootProject) {
		//// @formatter:off
		int maxLenghtEntityName = bootProject.getEntities().stream()
				.map(EJpaEntity::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
//		bootProject.getEntities().stream()
//			.forEach(entity -> ConsoleLog.print(ConsoleLog.cyan(StringUtils.rightPad(entity.getName(), maxLenghtEntityName)), " - ", ConsoleLog.gray(entity.getPackage().getName())));
		// @formatter:on
		for (EJpaEntity entity : bootProject.getEntities()) {
			System.out.println(entity.getName());
			for (EJpaAttribute attribute : entity.getAttributes()) {
				System.out.print(String.format("\t%s: %s ", attribute.getName(), attribute.getType().getName()));
				attribute.getJpaRelationship().ifPresent(relationship -> System.out.println(" Target: " + relationship.getTargetEntity().getName() + " Bidirectional: " + relationship.isBidirectional()));
				System.out.println();
			}
		}

		//		this.getProject().getAssociatedAngularProject().ifPresent(a -> System.out.println(a.getName()));
	}
}
