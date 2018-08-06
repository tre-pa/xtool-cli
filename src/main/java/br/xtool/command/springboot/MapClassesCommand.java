package br.xtool.command.springboot;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.service.BootService;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.visitor.Visitor;
import br.xtool.core.visitor.impl.JacksonVisitor;
import br.xtool.core.visitor.impl.JavaxValidationVisitor;
import br.xtool.core.visitor.impl.JpaVisitor;
import br.xtool.core.visitor.impl.LombokVisitor;

@ShellComponent
public class MapClassesCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@Autowired
	private ApplicationContext applicationContext;
	//
	//	@Autowired
	//	private JpaClassVisitor jpaClassVisitor;

	@ShellMethod(key = "map:classes", value = "Mapeia uma classe do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Desabilita o mapeamento JPA da classe", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desabilita o mapeamento Lombok da classe", defaultValue = "false", arity = 0) Boolean noLombok,
			@ShellOption(help = "Desabilita o mapeamento Jackson da classe", defaultValue = "false", arity = 0) Boolean noJackson) {
	// @formatter:on
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		//EUmlClassDiagram umlClassDiagram = getDomainClassDiagram(bootProject);

		Set<Visitor> visitors = new HashSet<>();
		visitors.add(this.applicationContext.getBean(JavaxValidationVisitor.class));
		if (!noLombok) visitors.add(this.applicationContext.getBean(LombokVisitor.class));
		if (!noJpa) visitors.add(this.applicationContext.getBean(JpaVisitor.class));
		if (!noJackson) visitors.add(this.applicationContext.getBean(JacksonVisitor.class));

		this.bootService.convertUmlClassDiagramToJavaClasses(bootProject, visitors);
	}

}
