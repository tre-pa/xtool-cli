package br.xtool.core.representation;

import java.util.Optional;

import org.jdom2.Element;

public interface EDependency {

	public String getGroupId();

	String getArtifactId();

	Optional<String> getVersion();

	Element getAsDom();

}
