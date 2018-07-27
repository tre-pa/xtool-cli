package br.xtool.core.service.impl;

import java.io.StringWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.service.ShellService;
import br.xtool.core.service.WorkspaceService;
import lombok.SneakyThrows;

@Service
public class ShellServiceImpl implements ShellService {

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	public int runCmd(String command) {
		return runCmd(this.workspaceService.getWorkingProject().getPath(), command, new HashMap<>());
	}

	@Override
	public int runCmd(String command, Map<String, Object> vars) {
		return runCmd(this.workspaceService.getWorkingProject().getPath(), command, vars);
	}

	@Override
	public int runCmd(Path path, String command) {
		return runCmd(path, command, new HashMap<>());
	}

	@Override
	@SneakyThrows
	public int runCmd(Path path, String command, Map<String, Object> vars) {
		StringWriter stringWriter = new StringWriter();
		VelocityContext velocityContext = new VelocityContext(vars);
		this.velocityEngine.evaluate(velocityContext, stringWriter, new String(), command);
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("sh", "-c", stringWriter.toString());
		processBuilder.directory(path.toFile());
		processBuilder.inheritIO();
		Process process = processBuilder.start();
		return process.waitFor();
	}

}
