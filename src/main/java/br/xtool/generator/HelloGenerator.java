package br.xtool.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.generator.SpringBootGeneratorCommand;
import br.xtool.core.model.Entity;
import br.xtool.core.provider.EntityValueProvider;

@ShellGeneratorComponent(templatePath = "generators/hello")
public class HelloGenerator extends SpringBootGeneratorCommand {

	@ShellMethod(key = "gen-hello", value = "Gerador de Hello")
	public void run(@ShellOption(valueProvider = EntityValueProvider.class) Entity entity) throws IOException {
		Map<String, Object> vars = new HashMap<>();

		System.out.println("Entidade Selecionada: " + entity.getName());

		// this.copyTpl("hello.vm", "target/app/hello-gen.txt", vars);
		getProject().getEntities().stream().forEach(j -> System.out.println(j.getName()));
	}

}
