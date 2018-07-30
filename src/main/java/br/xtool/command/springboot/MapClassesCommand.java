package br.xtool.command.springboot;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlClassDiagram;
import br.xtool.core.representation.impl.EJavaClassImpl;
import br.xtool.core.service.BootService;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.visitor.impl.JpaVisitor;

@ShellComponent
public class MapClassesCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@ShellMethod(key = "map:classes", value = "Mapeia uma classe do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Desabilita o mapeamento JPA da classe", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desabilita o mapeamento Lombok da classe", defaultValue = "false", arity = 0) Boolean noLombok,
			@ShellOption(help = "Desabilita o mapeamento Jackson da classe", defaultValue = "false", arity = 0) Boolean noJackson) {
	// @formatter:on
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		EUmlClassDiagram classDiagram = bootProject.getDomainClassDiagram()
				.orElseThrow(() -> new IllegalArgumentException("O projeto não possui o diagram de classe em docs/diagrams/domain-class.md"));
		for (EUmlClass umlClass : classDiagram.getClasses()) {
			JavaClassSource javaClassSource = umlClass.convertToJavaClassSource(bootProject);
			if (!noJpa) this.jpaVisitor(umlClass, javaClassSource);
			this.bootService.save(bootProject.getMainSourceFolder(), new EJavaClassImpl(bootProject, javaClassSource));
		}
	}

	private void jpaVisitor(EUmlClass umlClass, JavaClassSource javaClassSource) {
		JpaVisitor jpaVisitor = new JpaVisitor(javaClassSource);
		jpaVisitor.visit(umlClass);
	}
}
