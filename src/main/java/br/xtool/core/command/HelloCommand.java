package br.xtool.core.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import br.xtool.core.FS;

@ShellComponent
public class HelloCommand {

	@Autowired
	private FS fs;

	@ShellMethod(value = "Hello")
	public void hello(@ShellOption(value = "Teste Hello") String name) throws IOException {
		Map<String, Object> vars = new HashMap<>();
		vars.put("name", name);
		fs.copyTpl("hello.vm", "/home/jcruz/Documentos/workspace-sts-3.9.4.RELEASE/xtool-cli/target/hello.txt", vars);
		fs.copy("pic.txt", "/home/jcruz/Documentos/workspace-sts-3.9.4.RELEASE/xtool-cli/target/pic.txt");
	}
}
