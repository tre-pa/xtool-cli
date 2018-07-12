package br.xtool.core.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.WorkContext;

@ShellComponent
public class RefreshCommand {

	@Autowired
	private WorkContext workContext;

	@ShellMethod(value = "Atualiza o projeto de trabalho", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void refresh() {
		if (this.workContext.getSpringBootProject().isPresent()) {
			this.workContext.getSpringBootProject().get().refresh();
		} else if (this.workContext.getAngularProject().isPresent()) {
			this.workContext.getAngularProject().get().refresh();
		}
	}
}
