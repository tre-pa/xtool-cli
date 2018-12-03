package br.xtool.command.springboot;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.provider.EJpaRepositoryValueProvider;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.service.BootProjectService;
import br.xtool.service.WorkspaceService;
import br.xtool.templates.RestTemplates;

//@Profile("in-dev")
@ShellComponent
public class GenRestCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootProjectService bootProjectService;

	@ShellMethod(key = "gen:rest", value = "Gera uma classe Rest em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Classe de repositório", valueProvider = EJpaRepositoryValueProvider.class) EJpaRepository repository) throws JDOMException, IOException {

		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);

		this.bootProjectService.createRest(bootProject, repository, rest -> {
			RestTemplates.genFindAll(rest, repository);
			RestTemplates.genFilter(rest, repository);
			RestTemplates.genFindById(rest, repository);
			RestTemplates.genInsertMethod(rest, repository);
			RestTemplates.genUpdateMethod(rest, repository);
			RestTemplates.genDeleteMethod(rest, repository);
//			RestTemplates.genCount(rest, repository);
		});
		//		this.bootProjectService.save(rest);

	}
}
