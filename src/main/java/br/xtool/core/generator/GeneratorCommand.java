package br.xtool.core.generator;

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
import br.xtool.core.PathContext;
import br.xtool.core.annotation.ShellGeneratorComponent;

public class GeneratorCommand {

	@Autowired
	private FS fs;

	@Autowired
	private PathContext pathCtx;

	@Autowired
	private VelocityEngine vEngine;

	@Autowired
	private Log log;

	private String destinationRoot;

	protected void copy(String source, String destination) throws IOException {
		String fSource = this.getFinalSource(source);
		String fDestination = this.getFinalDestination(destination);
		fs.copy(fSource, fDestination);
		log.print(log.green("\tCREATE ") + log.white(destination));
	}

	protected void copyTpl(String template, String destination, Map<String, Object> vars) throws IOException {
		VelocityContext vContext = new VelocityContext(vars);
		StringWriter stringWriter = new StringWriter();
		vEngine.evaluate(vContext, stringWriter, new String(), destination);
		destination = stringWriter.toString();

		String fTemplate = this.getFinalSource(template);
		String fDestination = this.getFinalDestination(destination);
		fs.copyTpl(fTemplate, fDestination, vars);
		log.print(log.green("\tCREATE ") + log.white(destination));
	}

	protected void copyTpl(String template, String destination, Map<String, Object> vars, Supplier<Boolean> exp) throws IOException {
		if(exp.get()) {
			this.copyTpl(template, destination, vars);
		}
	}

	protected void setDestinationRoot(String destinationRoot) {
		this.destinationRoot = destinationRoot;
	}

	private String getFinalSource(String path) {
		String prefix = this.getClass().getAnnotation(ShellGeneratorComponent.class).templatePath();
		return FilenameUtils.concat(prefix, path);
	}

	private String getFinalDestination(String destination) {
		return FilenameUtils.concat(FilenameUtils.concat(pathCtx.getWorkingDirectory(), destinationRoot), destination);
	}

}
