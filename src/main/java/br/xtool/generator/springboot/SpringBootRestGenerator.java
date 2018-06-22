package br.xtool.generator.springboot;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.XtoolCliApplication;
import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.generator.SpringBootGeneratorCommand;
import br.xtool.core.model.Repository;
import br.xtool.core.provider.RepositoryValueProvider;

@ShellGeneratorComponent(templatePath = "generators/springboot/rest")
public class SpringBootRestGenerator extends SpringBootGeneratorCommand {

	@ShellMethod(key = "gen-springboot-rest", value = "Gera uma classe Rest", group = XtoolCliApplication.SPRINGBOOT_COMMAND_GROUP)
	public void run() {
		TextIO textIO = TextIoFactory.getTextIO();
		//String user = textIO.newStringInputReader().withMinLength(5).read("Username");
		//textIO.
		//System.out.println(user);
	}
}
