package br.xtool.command.springboot;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.Workspace;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.implementation.NgProject;
import br.xtool.core.implementation.SpringBootProject;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.visitor.Visitor;
import br.xtool.core.visitor.impl.JacksonVisitor;
import br.xtool.core.visitor.impl.JavaxValidationVisitor;
import br.xtool.core.visitor.impl.JpaVisitor;
import br.xtool.core.visitor.impl.LombokVisitor;

//@ShellComponent
public class GenEntitiesCommand extends SpringBootAware {

	@Autowired
	private Workspace workspaceService;

	@Autowired
	private SpringBootProject bootProjectService;

	@Autowired
	private NgProject ngProjectService;

	@Autowired
	private ApplicationContext applicationContext;

	@ShellMethod(key = "gen:entities", value = "Gera as entidades Java do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Gera as classes Typescript no projeto Angular associado", defaultValue = "false", arity = 0) Boolean ngEntities) {
	// @formatter:on
		SpringBootProjectRepresentation bootProject = this.workspaceService.getWorkingProject(SpringBootProjectRepresentation.class);

		Set<Visitor> visitors = new HashSet<>();
		visitors.add(this.applicationContext.getBean(JavaxValidationVisitor.class));
		visitors.add(this.applicationContext.getBean(LombokVisitor.class));
		visitors.add(this.applicationContext.getBean(JpaVisitor.class));
		visitors.add(this.applicationContext.getBean(JacksonVisitor.class));

		this.bootProjectService.umlEnumsToJavaEnums(bootProject);
//		this.bootProjectService.umlClassesToJavaClasses(bootProject, visitors);
		bootProject.refresh();
		if (ngEntities) this.genNgEntities(bootProject);
	}

	private void genNgEntities(SpringBootProjectRepresentation bootProject) {
		bootProject.getAssociatedAngularProject().ifPresent(ngProject -> {
			bootProject.getEnums().forEach(javaEnum -> ngProjectService.createNgEnum(ngProject, javaEnum));
			bootProject.getEntities().forEach(entity -> ngProjectService.createNgEntity(ngProject, entity));
		});
	}

}
