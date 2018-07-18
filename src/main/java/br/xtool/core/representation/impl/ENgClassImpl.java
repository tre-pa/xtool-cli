package br.xtool.core.representation.impl;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.angular.ENgClass;
import lombok.Getter;
import lombok.ToString;

/**
 * Representa uma classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
@ToString(of = { "name", "fileName" })
public class ENgClassImpl implements ENgClass {

	@Getter
	private File file;

	public ENgClassImpl(File file) {
		super();
		this.file = file;
	}

	@Override
	public String getName() {
		// @formatter:off
		return Stream.of(FilenameUtils.getBaseName(this.getFile().getPath()).split("\\-|\\."))
			.map(StringUtils::capitalize)
			.collect(Collectors.joining());
		// @formatter:on
	}

	@Override
	public String getFileName() {
		return FilenameUtils.getName(this.getFile().getPath());
	}

	@Override
	public int compareTo(ENgClass o) {
		return this.getName().compareTo(o.getName());
	}

}
