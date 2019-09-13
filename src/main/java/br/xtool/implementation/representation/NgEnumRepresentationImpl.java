package br.xtool.implementation.representation;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import br.xtool.representation.angular.NgEnumRepresentation;

public class NgEnumRepresentationImpl extends NgTypeRepresentationImpl implements NgEnumRepresentation {

	public NgEnumRepresentationImpl(Path path) {
		super(path);
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
	public int compareTo(NgEnumRepresentation o) {
		return this.getName().compareTo(o.getName());
	}
}
