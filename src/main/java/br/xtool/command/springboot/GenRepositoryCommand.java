package br.xtool.command.springboot;

import java.io.IOException;
import java.util.Objects;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.provider.EJpaEntityValueProvider;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaEntity;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.core.service.BootProjectService;
import br.xtool.core.service.WorkspaceService;
import br.xtool.core.template.impl.JpaSpecificationTemplates;

/**
 * Comando que gera um classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
@ShellComponent
public class GenRepositoryCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootProjectService bootProjectService;

	@ShellMethod(key = "gen:repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EJpaEntityValueProvider.class, defaultValue = "") EJpaEntity entity)
			throws IOException, JDOMException {
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		if (Objects.isNull(entity)) {
			bootProject.getEntities().stream().forEach(_entity -> this.createRepository(_entity, bootProject));
			return;
		}
		createRepository(entity, bootProject);

	}

	private void createRepository(EJpaEntity entity, EBootProject bootProject) {
		this.bootProjectService.createSpecification(bootProject, entity, specification -> {
			JpaSpecificationTemplates.genFilterSpecfication(specification);
		});
		EJpaRepository jpaRepository = this.bootProjectService.createRepository(bootProject, entity);
		this.bootProjectService.save(jpaRepository);
	}
}
