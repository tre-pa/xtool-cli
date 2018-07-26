package br.xtool.core.service.impl;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.service.FileService;
import br.xtool.core.template.Resource;
import br.xtool.core.template.impl.ResourceImpl;
import lombok.SneakyThrows;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	@SneakyThrows
	public Collection<Resource> getTemplates(Path rootPath, PathMatcher pathMatcher, Map<String, Object> vars) {
		VelocityContext velocityContext = new VelocityContext(vars);
		// @formatter:off
		return Files.walk(rootPath)
				.filter(Files::isRegularFile)
			.map(path -> new ResourceImpl(rootPath,rootPath.relativize(path),this.velocityEngine, velocityContext))
			.collect(Collectors.toList());
		// @formatter:on
	}

	@SneakyThrows
	private String readFileAsString(Path path) {
		return new String(Files.readAllBytes(path));
	}

	@SneakyThrows
	private void copy(Resource resource, Path path) {
		Path finalPath = path.resolve(resource.getPath());
		if (Files.notExists(finalPath.getParent())) Files.createDirectories(finalPath.getParent());
		OutputStream os = Files.newOutputStream(finalPath);
		os.write(resource.read());
		os.flush();
		os.close();
		ConsoleLog.print("\t" + ConsoleLog.green("[+] "), resource.getPath().toString());
	}

	@Override
	public void copy(Collection<Resource> resources, Path path) {
		resources.forEach(resource -> this.copy(resource, path));
	}

}
