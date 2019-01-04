package br.xtool.core.representation.impl;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.NgEnumRepresentation;
import lombok.Getter;

public class ENgEnumImpl implements NgEnumRepresentation {
	@Getter
	private Path path;

	public ENgEnumImpl(Path path) {
		super();
		this.path = path;
	}

	@Override
	public String getName() {
		// @formatter:off
		return Stream.of(FilenameUtils.getBaseName(this.getPath().toString()).split("\\-|\\."))
			.map(StringUtils::capitalize)
			.collect(Collectors.joining());
		// @formatter:on
	}

	@Override
	public String getFileName() {
		return this.getPath().getFileName().toString();
	}

	@Override
	public int compareTo(NgEnumRepresentation o) {
		return this.getName().compareTo(o.getName());
	}
}
