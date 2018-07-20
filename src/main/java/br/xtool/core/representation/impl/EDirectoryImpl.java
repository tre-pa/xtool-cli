package br.xtool.core.representation.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.collect.ImmutableSet;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgPackage;
import br.xtool.core.representation.EProject.ProjectType;
import br.xtool.core.representation.ESBootPom;
import lombok.Getter;

public class EDirectoryImpl implements EDirectory {

	@Getter
	private String path;

	@Getter(lazy = true)
	private final ProjectType projectType = buildProjectType();

	// @formatter:off
	private Set<Function<EDirectoryImpl, ProjectType>> typeResolvers = 
			ImmutableSet.of(
					new SpringBootProjectTypeResolver(),
					new AngularProjectProjectTypeResolver()
			);
	// @formatter:on

	private EDirectoryImpl(String directory) {
		super();
		this.path = FilenameUtils.normalizeNoEndSeparator(directory, true);
	}

	@Override
	public List<File> getAllFiles() {
		try (Stream<Path> pathStrem = Files.walk(Paths.get(this.path))) {
			// @formatter:off
			return pathStrem
					.filter(Files::isRegularFile)
					.filter(p -> !p.startsWith(FilenameUtils.concat(this.path, "target")))
					.filter(p -> !p.startsWith(FilenameUtils.concat(this.path, ".git")))
					.filter(p -> !p.startsWith(FilenameUtils.concat(this.path, ".settings")))
					.filter(p -> !p.startsWith(FilenameUtils.concat(this.path, "node_modules")))
					.filter(p -> !p.startsWith(FilenameUtils.concat(this.path, "dist")))
					.map(p -> p.toFile())
					.collect(Collectors.toList());
			// @formatter:on
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	protected ProjectType buildProjectType() {
		// @formatter:off
		return this.typeResolvers.stream()
				.map(fun -> fun.apply(this))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(ProjectType.NONE);
		// @formatter:on
	}

	@Override
	public List<EDirectory> getChildrenDirectories() {
		// @formatter:off
		try {
			return Files.list(Paths.get(this.path))
					.filter(Files::isDirectory)
					.map(Path::toFile)
					.map(file -> EDirectoryImpl.of(file.getAbsolutePath()))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
		// @formatter:on
	}

	public static EDirectoryImpl of(String path) {
		if (!Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException(String.format("O diretório %s não existe", path));
		}
		return new EDirectoryImpl(path);
	}

	private class SpringBootProjectTypeResolver implements Function<EDirectoryImpl, ProjectType> {

		@Override
		public @Nullable ProjectType apply(@Nullable EDirectoryImpl dr) {
			String pomFile = FilenameUtils.concat(dr.getPath(), "pom.xml");
			if (Files.exists(Paths.get(pomFile))) {
				ESBootPom ePom = ESBootPomImpl.of(pomFile);
				if (ePom.getParentVersion().isPresent()) {
					if (ePom.getParentGroupId().get().equals("org.springframework.boot")) return ProjectType.SPRINGBOOT_PROJECT;
				}
			}
			return null;
		}
	}

	private class AngularProjectProjectTypeResolver implements Function<EDirectoryImpl, ProjectType> {

		@Override
		public ProjectType apply(EDirectoryImpl dr) {
			String packageJsonFile = FilenameUtils.concat(dr.getPath(), "package.json");
			if (Files.exists(Paths.get(packageJsonFile))) {
				Optional<ENgPackage> ngPackage = ENgPackageImpl.of(packageJsonFile);
				if (ngPackage.isPresent()) {
					Map<String, String> dependencies = ngPackage.get().getDependencies();
					if (dependencies.containsKey("@angular/core")) return ProjectType.ANGULAR_PROJECT;
				}
			}
			return null;
		}

	}

}
