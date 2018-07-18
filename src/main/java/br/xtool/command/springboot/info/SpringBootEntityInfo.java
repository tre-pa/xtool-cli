package br.xtool.command.springboot.info;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import br.xtool.XtoolCliApplication;
import br.xtool.core.ConsoleLog;
import br.xtool.core.aware.SpringBootAware;
import br.xtool.core.representation.EJavaEntity;

@ShellComponent
public class SpringBootEntityInfo extends SpringBootAware {

	@ShellMethod(key = "info:entity", value = "Exibe informações sobre as entidades JPA do projeto", group = XtoolCliApplication.XTOOL_COMMAND_GROUP)
	public void run() {
		infoAllEntities();
	}

	private void infoAllEntities() {
		//// @formatter:off
		int maxLenghtEntityName = this.getProject().getEntities().stream()
				.map(EJavaEntity::getName)
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(10);
		this.getProject().getEntities().stream()
			.forEach(entity -> ConsoleLog.print(ConsoleLog.cyan(StringUtils.rightPad(entity.getName(), maxLenghtEntityName)), " - ", ConsoleLog.gray(entity.getPackage().getName())));
		
//		this.getProject().getAssociatedAngularProject().ifPresent(a -> System.out.println(a.getName()));
		// @formatter:on
	}
}
