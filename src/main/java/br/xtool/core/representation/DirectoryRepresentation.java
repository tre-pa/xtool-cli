package br.xtool.core.representation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.collect.ImmutableSet;

import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

public class DirectoryRepresentation {

	@Getter
	private String path;

	// @formatter:off
	private Set<Function<DirectoryRepresentation, ProjectType>> typeResolvers = 
			ImmutableSet.of(
					new SpringBoot1ProjectTypeResolver()
			);
	// @formatter:on

	public DirectoryRepresentation(String directory) {
		super();
		this.path = FilenameUtils.normalizeNoEndSeparator(directory, true);
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
					.filter(p -> !p.startsWith(FilenameUtils.concat(path, "node_modules")))
					.filter(p -> !p.startsWith(FilenameUtils.concat(path, "dist")))
					.map(p -> p.toFile())
					.collect(Collectors.toList());
			// @formatter:on
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public ProjectType getProjectType() {
		// @formatter:off
		return this.typeResolvers.stream()
				.map(fun -> fun.apply(this))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(ProjectType.NONE);
		// @formatter:on
	}

	private class SpringBoot1ProjectTypeResolver implements Function<DirectoryRepresentation, ProjectType> {

		@Override
		public @Nullable ProjectType apply(@Nullable DirectoryRepresentation dr) {
			String pomFile = FilenameUtils.concat(dr.getPath(), "pom.xml");
			if (Files.exists(Paths.get(pomFile))) {
				Optional<PomRepresentation> pomRepresentation = PomRepresentation.of(pomFile);
				if (pomRepresentation.isPresent()) {
					Pattern pattern = Pattern.compile("1.5.\\d\\d?.RELEASE");
					Matcher matcher = pattern.matcher(pomRepresentation.get().getParentVersion());
					if (matcher.matches()) {
						return ProjectType.SPRINGBOOT1_PROJECT;
					}
				}
			}
			return null;
		}
	}
}
