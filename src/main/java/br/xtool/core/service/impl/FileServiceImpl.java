package br.xtool.core.service.impl;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.EProject;
import br.xtool.core.representation.EResource;
import br.xtool.core.representation.impl.EResourceImpl;
import br.xtool.service.FileService;
import lombok.SneakyThrows;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private VelocityEngine velocityEngine;

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.FileService#getTemplates(java.nio.file.Path, java.util.Map)
	 */
	@SneakyThrows
	private Collection<EResource> getResources(Path rootPath, Map<String, Object> vars) {
		Path realRootPath = EResource.ROOT_PATH.resolve(rootPath);
		VelocityContext velocityContext = new VelocityContext(vars);
		// @formatter:off
		return Files.walk(realRootPath)
			.filter(Files::isRegularFile)
			.map(path -> new EResourceImpl(realRootPath,realRootPath.relativize(path),this.velocityEngine, velocityContext))
			.collect(Collectors.toList());
		// @formatter:on
	}

	private EResource getResource(Path resourcePath, Map<String, Object> vars) {
		Path realRootPath = EResource.ROOT_PATH.resolve(resourcePath);
		VelocityContext velocityContext = new VelocityContext(vars);
		return new EResourceImpl(realRootPath, resourcePath, this.velocityEngine, velocityContext);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.FileService#copy(java.util.Collection, br.xtool.core.representation.EProject)
	 */
	@Override
	public <T extends EProject> void copy(Path resourcePath, Map<String, Object> vars, T destProject) {
		if (Files.isDirectory(EResource.ROOT_PATH.resolve(resourcePath))) {
			Collection<EResource> resources = this.getResources(resourcePath, vars);
			resources.forEach(resource -> this.copy(resource, destProject.getPath()));
			return;
		}
		EResource resource = this.getResource(resourcePath, vars);
		this.copy(resource, destProject.getPath());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.service.FileService#copy(java.util.Collection, java.nio.file.Path)
	 */
	@Override
	public void copy(Path resourcePath, Map<String, Object> vars, Path path) {
		if (Files.isDirectory(EResource.ROOT_PATH.resolve(resourcePath))) {
			Collection<EResource> resources = this.getResources(resourcePath, vars);
			resources.forEach(resource -> this.copy(resource, path));
			return;
		}
		//		System.out.println("Resource: " + Files.isDirectory(EResource.ROOT_PATH.resolve(resourcePath)));
		EResource resource = this.getResource(resourcePath, vars);
		this.copy(resource, path);
	}

	@SneakyThrows
	private String readFileAsString(Path path) {
		return new String(Files.readAllBytes(path));
	}

	@SneakyThrows
	private void copy(EResource resource, Path path) {
		Path finalPath = path.resolve(resource.getRelativePath());
		if (Files.notExists(finalPath.getParent())) Files.createDirectories(finalPath.getParent());
		OutputStream os = Files.newOutputStream(finalPath);
		os.write(resource.read());
		os.flush();
		os.close();
		ConsoleLog.print(ConsoleLog.green("[+] "), resource.getRelativePath().toString());
	}

}
