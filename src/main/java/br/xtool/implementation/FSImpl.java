package br.xtool.implementation;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import br.xtool.implementation.representation.ResourceRepresentationImpl;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.xtool.core.FS;
import br.xtool.representation.ProjectRepresentation;
import br.xtool.representation.ResourceRepresentation;
import lombok.SneakyThrows;

@Service
public class FSImpl implements FS {

	@Autowired
	private VelocityEngine velocityEngine;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.FileService#getTemplates(java.nio.file.Path, java.util.Map)
	 */
	@SneakyThrows
	private Collection<ResourceRepresentation> getResources(Path rootPath, Map<String, Object> vars) {
		Path realRootPath = ResourceRepresentation.ROOT_PATH.resolve(rootPath);
		VelocityContext velocityContext = new VelocityContext(vars);
		// @formatter:off
		return Files.walk(realRootPath)
			.filter(Files::isRegularFile)
			.map(path -> new ResourceRepresentationImpl(realRootPath,realRootPath.relativize(path),velocityEngine, velocityContext))
			.collect(Collectors.toList());
		// @formatter:on
	}

	private ResourceRepresentation getResource(Path resourcePath, Map<String, Object> vars) {
		Path realRootPath = ResourceRepresentation.ROOT_PATH.resolve(resourcePath);
		VelocityContext velocityContext = new VelocityContext(vars);
		return new ResourceRepresentationImpl(realRootPath, resourcePath, velocityEngine, velocityContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.FileService#copy(java.util.Collection, br.xtool.core.representation.EProject)
	 */
	@Override
	public <T extends ProjectRepresentation> void copy(Path resourcePath, Map<String, Object> vars, T destProject) {
		if (Files.isDirectory(ResourceRepresentation.ROOT_PATH.resolve(resourcePath))) {
			Collection<ResourceRepresentation> resources = getResources(resourcePath, vars);
			resources.forEach(resource -> this.copy(resource, destProject.getPath()));
			return;
		}
		ResourceRepresentation resource = getResource(resourcePath, vars);
		this.copy(resource, destProject.getPath());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.service.FileService#copy(java.util.Collection, java.nio.file.Path)
	 */
	@Override
	public void copy(Path resourcePath, Map<String, Object> vars, Path path) {
		if (Files.isDirectory(ResourceRepresentation.ROOT_PATH.resolve(resourcePath))) {
			Collection<ResourceRepresentation> resources = getResources(resourcePath, vars);
			resources.forEach(resource -> this.copy(resource, path));
			return;
		}
		ResourceRepresentation resource = getResource(resourcePath, vars);
		this.copy(resource, path);
	}

	@SneakyThrows
	private String readFileAsString(Path path) {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	@SneakyThrows
	private void copy(ResourceRepresentation resource, Path path) {
		Path finalPath = path.resolve(resource.getRelativePath());
		if (Files.notExists(finalPath.getParent())) Files.createDirectories(finalPath.getParent());
		OutputStream os = Files.newOutputStream(finalPath);
		os.write(resource.read());
		os.flush();
		os.close();
	}

}
