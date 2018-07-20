package br.xtool.command.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.event.ChangeWorkingProjectEvent;
import br.xtool.core.provider.EProjectValueProvider;
import br.xtool.core.representation.EProject;

@ShellComponent
public class UseCommand {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@ShellMethod(value = "Define o projeto de trabalho atual", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void use(@ShellOption(help = "Nome do projeto do workspace", valueProvider = EProjectValueProvider.class) EProject project) {
		this.eventPublisher.publishEvent(new ChangeWorkingProjectEvent(project));
	}
}
