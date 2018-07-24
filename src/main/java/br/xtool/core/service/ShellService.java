package br.xtool.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class ShellService {

	@Autowired
	private FileService fs;

	@Autowired
	private WorkspaceService workspaceService;

	@SneakyThrows
	public int runCmd(String command) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("sh", "-c", command);
		processBuilder.directory(this.workspaceService.getWorkingProject().getDirectory().getPath().toFile());
		processBuilder.inheritIO();
		Process process = processBuilder.start();
		return process.waitFor();
	}

	@SneakyThrows
	public int runCmd(String command, Map<String, Object> vars) {
		throw new UnsupportedOperationException();
		//		return this.runCmd(this.fs.getTemplate(command, vars));
	}

}
