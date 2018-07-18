package br.xtool.core.service;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.WorkContext;
import lombok.SneakyThrows;

@Component
public class ShellService {

	@Autowired
	private FileService fs;

	@Autowired
	private WorkContext workContext;

	@SneakyThrows
	public int run(String command) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("sh", "-c", command);
		processBuilder.directory(new File(workContext.getDirectory().getPath()));
		processBuilder.inheritIO();
		Process process = processBuilder.start();
		return process.waitFor();
	}

	@SneakyThrows
	public int run(String command, Map<String, Object> vars) {
		return this.run(fs.inlineTemplate(command, vars));
	}

}
