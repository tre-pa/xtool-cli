package br.xtool.core.command;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;

import br.xtool.core.FS;
import br.xtool.core.Log;
import br.xtool.core.PathService;
import br.xtool.core.annotation.ShellGeneratorComponent;

public class XCommand {

	@Autowired
	private FS fs;

	@Autowired
	private PathService pathService;

	@Autowired
	private VelocityEngine vEngine;


	private String destinationRoot="";

	protected void copy(String source, String destination) throws IOException {
		String fSource = this.getFinalSource(source);
		String fDestination = this.getFinalDestination(destination);
		fs.copy(fSource, fDestination);
		Log.print(Log.green("\tCREATE ") + Log.white(destination));
	}
	
	protected void copy(String source, String destination, Supplier<Boolean> exp) throws IOException {
		if(exp.get()) {
			this.copy(source, destination);
		}
	}

	protected void copyTpl(String template, String destination, Map<String, Object> vars) throws IOException {
		VelocityContext vContext = new VelocityContext(vars);
		StringWriter stringWriter = new StringWriter();
		vEngine.evaluate(vContext, stringWriter, new String(), destination);
		destination = stringWriter.toString();

		String fTemplate = this.getFinalSource(template);
		String fDestination = this.getFinalDestination(destination);
		fs.copyTpl(fTemplate, fDestination, vars);
		Log.print(Log.green("\tCREATE ") + Log.white(destination));
	}

	protected void copyTpl(String template, String destination, Map<String, Object> vars, Supplier<Boolean> exp) throws IOException {
		if (exp.get()) {
			this.copyTpl(template, destination, vars);
		}
	}
	
	protected void changeWorkingDirectoryToDestinationRoot() {
		if(StringUtils.isNotEmpty(this.destinationRoot)) {
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
		String prefix = this.getClass().getAnnotation(ShellGeneratorComponent.class).templatePath();
		return FilenameUtils.concat(prefix, path);
	}

	private String getFinalDestination(String destination) {
		return FilenameUtils.concat(FilenameUtils.concat(pathService.getWorkingDirectory(), destinationRoot), destination);
	}

}
