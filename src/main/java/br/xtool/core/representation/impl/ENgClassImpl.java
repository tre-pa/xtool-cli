package br.xtool.core.representation.impl;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.ENgClass;
import lombok.Getter;

/**
 * Representa uma classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
public class ENgClassImpl implements ENgClass {

	@Getter
	private Path path;

	public ENgClassImpl(Path path) {
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
	public int compareTo(ENgClass o) {
		return this.getName().compareTo(o.getName());
	}

}
