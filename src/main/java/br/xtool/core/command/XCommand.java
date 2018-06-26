package br.xtool.core.command;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.xtool.core.FS;
import br.xtool.core.Log;
import br.xtool.core.PathService;
import br.xtool.core.annotation.Template;

public class XCommand {

	@Autowired
	private FS fs;

	@Autowired
	private PathService pathService;

	private String destinationRoot = "";

	protected void copy(String source, String destination) throws IOException {
		String fSource = this.getFinalSource(source);
		String fDestination = this.getFinalDestination(destination);
		fs.copy(fSource, fDestination);
		Log.print(Log.bold(Log.green("\t[+] ")) + Log.purple("File: ") + Log.white(destination));
	}

	protected void copy(String source, String destination, Supplier<Boolean> exp) throws IOException {
		if (exp.get()) {
			this.copy(source, destination);
		}
	}

	protected void copyTpl(String template, String destination, Map<String, Object> vars) throws IOException {

		String fTemplate = this.getFinalSource(template);
		String fDestination = this.getFinalDestination(destination);
		fs.copyTpl(fTemplate, fDestination, vars);
		Log.print(Log.bold(Log.green("\t[+] ")) + Log.purple("File: ") + Log.white(destination));
	}

	protected void copyTpl(String template, String destination, Map<String, Object> vars, Supplier<Boolean> exp) throws IOException {
		if (exp.get()) {
			this.copyTpl(template, destination, vars);
		}
	}

	protected void changeWorkingDirectoryToDestinationRoot() {
		if (StringUtils.isNotEmpty(this.destinationRoot)) {
			this.pathService.changeWorkingDirectory(FilenameUtils.concat(this.pathService.getWorkingDirectory(), this.getDestinationRoot()));
			Log.print(Log.white("\nDiretório de trabalho alterado para: "), Log.cyan(this.pathService.getWorkingDirectory()));
		}
	}

	protected void setDestinationRoot(String destinationRoot) {
		this.destinationRoot = destinationRoot;
	}

	protected String getDestinationRoot() {
		return destinationRoot;
	}

	private String getFinalSource(String path) {
		if (!this.getClass().isAnnotationPresent(Template.class)) {
			throw new RuntimeException("A classe " + this.getClass().getName().concat(" deve ser anotada com @Template"));
		}
		String prefix = this.getClass().getAnnotation(Template.class).path();
		return FilenameUtils.concat(prefix, path);
	}

	private String getFinalDestination(String destination) {
		return FilenameUtils.concat(FilenameUtils.concat(pathService.getWorkingDirectory(), destinationRoot), destination);
	}

}
