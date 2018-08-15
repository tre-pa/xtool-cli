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
import br.xtool.core.representation.EBootService;
import br.xtool.core.representation.EJpaRepository;
import br.xtool.service.BootService;
import br.xtool.service.WorkspaceService;

@ShellComponent
public class GenServiceCommand extends SpringBootAware {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BootService bootService;

	@ShellMethod(key = "gen:service", value = "Gera uma classe Service em um projeto Spring Boot", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run(@ShellOption(help = "Classe de repositório", valueProvider = EJpaRepositoryValueProvider.class) EJpaRepository repository) throws JDOMException, IOException {

		EBootProject bootProject = this.workspaceService.getWorkingProject(EBootProject.class);

		EBootService service = this.bootService.createService(bootProject, repository);
		this.bootService.save(bootProject.getMainSourceFolder(), service);

	}

}
