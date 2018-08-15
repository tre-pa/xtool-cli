package br.xtool.command.springboot;

import java.io.IOException;

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
import br.xtool.core.representation.EJpaProjection;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.core.representation.EJpaSpecification;
import br.xtool.service.BootService;
import br.xtool.service.WorkspaceService;

/**
 * Comando que gera um classe Repository no projeto Spring Boot
 * 
 * @author jcruz
 *
 */
//@Profile("in-dev")
@ShellComponent
public class GenJpaRepositoryCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@ShellMethod(key = "gen:repository", value = "Gera uma classe de Repository (JpaRepository) para entidade JPA em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Entidade JPA", valueProvider = EJpaEntityValueProvider.class) EJpaEntity entity) throws IOException, JDOMException {

		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		EJpaRepository repository = this.bootService.createRepository(bootProject, entity);
		this.bootService.save(bootProject.getMainSourceFolder(), repository);

		EJpaProjection projection = this.bootService.createProjection(bootProject, entity);
		this.bootService.save(bootProject.getMainSourceFolder(), projection);

		EJpaSpecification specification = this.bootService.createSpecification(bootProject, entity);
		this.bootService.save(bootProject.getMainSourceFolder(), specification);

	}
}
