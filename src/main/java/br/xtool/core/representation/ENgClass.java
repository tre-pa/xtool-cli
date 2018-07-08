package br.xtool.core.representation;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.ToString;

/**
 * Representa uma classe Typescript de um projeto Angular.
 * 
 * @author jcruz
 *
 */
@ToString(of = { "name", "fileName" })
public class ENgClass implements Comparable<ENgClass> {

	@Getter
	private File file;

	private String name;

	private String fileName;

	public ENgClass(File file) {
		super();
		this.file = file;
	}

	public String getName() {
		// @formatter:off
		return Stream.of(FilenameUtils.getBaseName(this.getFile().getPath()).split("\\-|\\."))
			.map(StringUtils::capitalize)
			.collect(Collectors.joining());
		// @formatter:on
	}

	public String getFileName() {
		return FilenameUtils.getName(this.getFile().getPath());
	}

	@Override
	public int compareTo(ENgClass o) {
		return this.getName().compareTo(o.getName());
	}

}
