package br.xtool.command.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.WorkContext;
import br.xtool.core.representation.EProject.ProjectType;

@ShellComponent
public class RefreshCommand {

	@Autowired
	private WorkContext workContext;

	@ShellMethod(value = "Atualiza o projeto de trabalho", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void refresh() {
		if (this.workContext.getDirectory().getProjectType().equals(ProjectType.SPRINGBOOT1_PROJECT)) {
			this.workContext.getSpringBootProject().refresh();
		} else if (this.workContext.getAngularProject().isPresent()) {
			this.workContext.getAngularProject().get().refresh();
		}
	}
}
