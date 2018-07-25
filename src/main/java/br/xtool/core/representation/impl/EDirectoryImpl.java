package br.xtool.core.representation.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.collect.ImmutableSet;

import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EDirectory;
import br.xtool.core.representation.ENgPackage;
import br.xtool.core.representation.EProject.Type;
import lombok.Getter;

@Deprecated
public class EDirectoryImpl implements EDirectory {

	@Getter
	private Path path;

	@Getter(lazy = true)
	private final Type projectType = buildProjectType();

	// @formatter:off
	private Set<Function<EDirectory, Type>> typeResolvers = 
			ImmutableSet.of(
					new SpringBootProjectTypeResolver(),
					new AngularProjectProjectTypeResolver()
			);
	// @formatter:on

	private EDirectoryImpl(Path path) {
		super();
		this.path = path.normalize();
	}

	@Override
	public List<File> getAllFiles() {
		try (Stream<Path> pathStrem = Files.walk(this.path)) {
			// @formatter:off
			return pathStrem
					.filter(Files::isRegularFile)
					.filter(p -> !p.startsWith("target"))
					.filter(p -> !p.startsWith(".git"))
					.filter(p -> !p.startsWith(".settings"))
					.filter(p -> !p.startsWith("node_modules"))
					.filter(p -> !p.startsWith("dist"))
					.map(p -> p.toFile())
					.collect(Collectors.toList());
			// @formatter:on
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public SortedSet<EDirectory> getDirectories() {
		// @formatter:off
		try {
			return Files.list(this.path)
					.filter(Files::isDirectory)
					.map(EDirectoryImpl::of)
					.collect(Collectors.toCollection(TreeSet::new));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TreeSet<>();
		// @formatter:on
	}

	@Override
	public SortedSet<EDirectory> getAllDirectories() {
		try (Stream<Path> pathStrem = Files.walk(this.path)) {
			// @formatter:off
			return pathStrem
				.filter(Files::isDirectory)
				.filter(p -> !p.startsWith("target"))
				.filter(p -> !p.startsWith(".git"))
				.filter(p -> !p.startsWith(".settings"))
				.filter(p -> !p.startsWith("node_modules"))
				.filter(p -> !p.startsWith("dist"))
				.map(EDirectoryImpl::of)
				.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new TreeSet<>();
	}

	public static EDirectoryImpl of(Path path) {
		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException(String.format("O diretório %s não existe", path));
		}
		return new EDirectoryImpl(path);
	}

	protected Type buildProjectType() {
		// @formatter:off
		return this.typeResolvers.stream()
				.map(fun -> fun.apply(this))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(Type.NONE);
		// @formatter:on
	}

	@Deprecated
	private class SpringBootProjectTypeResolver implements Function<EDirectory, Type> {

		@Override
		public @Nullable Type apply(@Nullable EDirectory directory) {
			Path pomFile = directory.getPath().resolve("pom.xml");
			if (Files.exists(pomFile)) {
				EBootPom ePom = EBootPomImpl.of(pomFile);
				if (ePom.getParentVersion().isPresent()) {
					if (ePom.getParentGroupId().get().equals("org.springframework.boot")) return Type.SPRINGBOOT_PROJECT;
				}
			}
			return null;
		}
	}

	@Deprecated
	private class AngularProjectProjectTypeResolver implements Function<EDirectory, Type> {

		@Override
		public Type apply(EDirectory dr) {
			Path packageJsonFile = dr.getPath().resolve("package.json");
			if (Files.exists(packageJsonFile)) {
				Optional<ENgPackage> ngPackage = ENgPackageImpl.of(packageJsonFile);
				if (ngPackage.isPresent()) {
					Map<String, String> dependencies = ngPackage.get().getDependencies();
					if (dependencies.containsKey("@angular/core")) return Type.ANGULAR_PROJECT;
				}
			}
			return null;
		}

	}

	@Override
	public int compareTo(EDirectory o) {
		return this.getPath().compareTo(o.getPath());
	}

}
