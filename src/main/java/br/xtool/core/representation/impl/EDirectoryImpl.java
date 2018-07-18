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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.collect.ImmutableSet;

import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgPackage;
import br.xtool.core.representation.EPom;
import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

public class EDirectoryImpl implements EDirectory {

	@Getter
	private String path;

	@Getter(lazy = true)
	private final ProjectType projectType = buildProjectType();

	// @formatter:off
	private Set<Function<EDirectoryImpl, ProjectType>> typeResolvers = 
			ImmutableSet.of(
					new SpringBoot1ProjectTypeResolver(),
					new Angular5ProjectProjectTypeResolver()
			);
	// @formatter:on

	private EDirectoryImpl(String directory) {
		super();
		this.path = FilenameUtils.normalizeNoEndSeparator(directory, true);
	}

	@Override
	public String getBaseName() {
		return FilenameUtils.getBaseName(this.path);
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

	public static EDirectoryImpl of(String path) {
		if (!Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException(String.format("O diretório %s não existe", path));
		}
		return new EDirectoryImpl(path);
	}

	private class SpringBoot1ProjectTypeResolver implements Function<EDirectoryImpl, ProjectType> {

		@Override
		public @Nullable ProjectType apply(@Nullable EDirectoryImpl dr) {
			String pomFile = FilenameUtils.concat(dr.getPath(), "pom.xml");
			if (Files.exists(Paths.get(pomFile))) {
				EPom pomRepresentation = EPomImpl.of(pomFile);
				Pattern pattern = Pattern.compile("1.5.\\d\\d?.RELEASE");
				Matcher matcher = pattern.matcher(pomRepresentation.getParentVersion());
				if (matcher.matches()) {
					return ProjectType.SPRINGBOOT1_PROJECT;
				}
			}
			return null;
		}
	}

	private class Angular5ProjectProjectTypeResolver implements Function<EDirectoryImpl, ProjectType> {

		@Override
		public ProjectType apply(EDirectoryImpl dr) {
			String packageJsonFile = FilenameUtils.concat(dr.getPath(), "package.json");
			if (Files.exists(Paths.get(packageJsonFile))) {
				Optional<ENgPackage> ngPackage = ENgPackageImpl.of(packageJsonFile);
				if (ngPackage.isPresent()) {
					Map<String, String> dependencies = ngPackage.get().getDependencies();
					if (dependencies.containsKey("@angular/core")) {
						Pattern pattern = Pattern.compile("[~|^]?5\\.\\d\\.\\d$");
						Matcher matcher = pattern.matcher(dependencies.get("@angular/core"));
						if (matcher.matches()) {
							return ProjectType.ANGULAR5_PROJECT;
						}
					}
				}
			}
			return null;
		}

	}

}
