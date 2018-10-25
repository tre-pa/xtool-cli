package br.xtool.command.springboot;

import java.io.IOException;
import java.util.Objects;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.provider.EJpaRepositoryValueProvider;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EBootService;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.service.BootProjectService;
import br.xtool.service.WorkspaceService;

@ShellComponent
public class GenServiceCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootProjectService bootProjectService;

	@ShellMethod(key = "gen:service", value = "Gera uma classe Service em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Classe de repositório", valueProvider = EJpaRepositoryValueProvider.class, defaultValue="") EJpaRepository repository) throws JDOMException, IOException {
		
		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);
		if(Objects.isNull(repository)) {
			bootProject.getRepositories().stream().forEach(_repository -> createService(_repository, bootProject));
			return;
		}
		createService(repository, bootProject);

	}

	private void createService(EJpaRepository repository, EBootProject bootProject) {
		EBootService service = this.bootProjectService.createService(bootProject, repository);
		this.bootProjectService.save(service);
		ConsoleLog.print(ConsoleLog.cyan(" + "), ConsoleLog.white(service.getQualifiedName()));
	}

}
