package br.xtool.core.representation;

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

import br.xtool.core.representation.angular.ENgPackage;
import br.xtool.core.representation.enums.ProjectType;
import lombok.Getter;

public class EDirectory {

	@Getter
	private String path;

	@Getter(lazy = true)
	private final ProjectType projectType = buildProjectType();

	// @formatter:off
	private Set<Function<EDirectory, ProjectType>> typeResolvers = 
			ImmutableSet.of(
					new SpringBoot1ProjectTypeResolver(),
					new Angular5ProjectProjectTypeResolver()
			);
	// @formatter:on

	private EDirectory(String directory) {
		super();
		this.path = FilenameUtils.normalizeNoEndSeparator(directory, true);
	}

	public String getBaseName() {
		return FilenameUtils.getBaseName(this.path);
	}

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

	public static EDirectory of(String path) {
		if (!Files.isDirectory(Paths.get(path))) {
			throw new IllegalArgumentException(String.format("O diretório %s não existe", path));
		}
		return new EDirectory(path);
	}

	private class SpringBoot1ProjectTypeResolver implements Function<EDirectory, ProjectType> {

		@Override
		public @Nullable ProjectType apply(@Nullable EDirectory dr) {
			String pomFile = FilenameUtils.concat(dr.getPath(), "pom.xml");
			if (Files.exists(Paths.get(pomFile))) {
				EPom pomRepresentation = EPom.of(pomFile);
				Pattern pattern = Pattern.compile("1.5.\\d\\d?.RELEASE");
				Matcher matcher = pattern.matcher(pomRepresentation.getParentVersion());
				if (matcher.matches()) {
					return ProjectType.SPRINGBOOT1_PROJECT;
				}
			}
			return null;
		}
	}

	private class Angular5ProjectProjectTypeResolver implements Function<EDirectory, ProjectType> {

		@Override
		public ProjectType apply(EDirectory dr) {
			String packageJsonFile = FilenameUtils.concat(dr.getPath(), "package.json");
			if (Files.exists(Paths.get(packageJsonFile))) {
				Optional<ENgPackage> ngPackage = ENgPackage.of(packageJsonFile);
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
