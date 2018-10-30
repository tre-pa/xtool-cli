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
import br.xtool.core.visitor.Visitor;
import br.xtool.core.visitor.impl.JacksonVisitor;
import br.xtool.core.visitor.impl.JavaxValidationVisitor;
import br.xtool.core.visitor.impl.JpaVisitor;
import br.xtool.core.visitor.impl.LombokVisitor;
import br.xtool.service.BootProjectService;
import br.xtool.service.NgProjectService;
import br.xtool.service.WorkspaceService;

@ShellComponent
public class GenEntitiesCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootProjectService bootProjectService;

	@Autowired
	private NgProjectService ngProjectService;

	@Autowired
	private ApplicationContext applicationContext;

	@ShellMethod(key = "gen:entities", value = "Gera as entidades Java do diagrama de classe UML.", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	// @formatter:off
	public void run(
			@ShellOption(help = "Desabilita o mapeamento JPA da classe", defaultValue = "false", arity = 0) Boolean noJpa,
			@ShellOption(help = "Desabilita o mapeamento Lombok da classe", defaultValue = "false", arity = 0) Boolean noLombok,
			@ShellOption(help = "Desabilita o mapeamento Jackson da classe", defaultValue = "false", arity = 0) Boolean noJackson,
			@ShellOption(help = "Gera as classes Typescript no projeto Angular associado", defaultValue = "false", arity = 0) Boolean ngEntities) {
	// @formatter:on
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);

		Set<Visitor> visitors = new HashSet<>();
		visitors.add(this.applicationContext.getBean(JavaxValidationVisitor.class));
		if (!noLombok) visitors.add(this.applicationContext.getBean(LombokVisitor.class));
		if (!noJpa) visitors.add(this.applicationContext.getBean(JpaVisitor.class));
		if (!noJackson) visitors.add(this.applicationContext.getBean(JacksonVisitor.class));

		this.bootProjectService.umlEnumsToJavaEnums(bootProject);
		this.bootProjectService.umlClassesToJavaClasses(bootProject, visitors);
		bootProject.refresh();
		if (ngEntities) this.genNgEntities(bootProject);
	}

	private void genNgEntities(EBootProject bootProject) {
		bootProject.getAssociatedAngularProject().ifPresent(ngProject -> {
			bootProject.getEntities().forEach(entity -> ngProjectService.createNgEntity(ngProject, entity));
		});
	}

}
