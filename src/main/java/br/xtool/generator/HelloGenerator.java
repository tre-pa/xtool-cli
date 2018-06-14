package br.xtool.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.core.annotation.ShellGeneratorComponent;
import br.xtool.core.generator.SpringBootGeneratorCommand;

@ShellGeneratorComponent(templatePath = "generators/hello")
public class HelloGenerator extends SpringBootGeneratorCommand {

	@ShellMethod(key = "gen-hello", value = "Gerador de Hello")
	public void run(@ShellOption String path) throws IOException {
		Map<String, Object> vars = new HashMap<>();
		
		//this.copyTpl("hello.vm", "target/app/hello-gen.txt", vars);
		getProject().getEntities()
			.stream()
			.forEach(j -> System.out.println(j.getName()));
	}

}
