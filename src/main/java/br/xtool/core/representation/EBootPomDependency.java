package br.xtool.core.representation;

import java.util.Optional;

import org.jdom2.Element;

public interface EBootPomDependency {

	public String getGroupId();

	String getArtifactId();

	Optional<String> getVersion();

	Element getAsDom();

}
