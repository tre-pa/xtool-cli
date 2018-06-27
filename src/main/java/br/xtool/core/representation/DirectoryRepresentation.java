package br.xtool.core.representation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import lombok.Getter;

public class DirectoryRepresentation {

	@Getter
	private String path;

	public DirectoryRepresentation(String directory) {
		super();
		this.path = directory;
	}

	public String getBaseName() {
		return FilenameUtils.getBaseName(path);
	}

	public List<File> listFilesRecursively() {
		try {
			// @formatter:off
			return Files.walk(Paths.get(this.path))
					.filter(Files::isRegularFile)
					.filter(p -> !p.startsWith(FilenameUtils.concat(path, "target")))
					.filter(p -> !p.startsWith(FilenameUtils.concat(path, ".git")))
					.filter(p -> !p.startsWith(FilenameUtils.concat(path, ".settings")))
					.map(p -> p.toFile())
					.collect(Collectors.toList());
			// @formatter:on
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
