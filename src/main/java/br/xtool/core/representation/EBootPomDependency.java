package br.xtool.core.representation;

import java.util.Optional;

import org.jdom2.Element;

/**
 * Representação de uma dependência do pom.xml de um projeto Spring Boot.
 * 
 * @author jcruz
 *
 */
public interface EBootPomDependency {

	public String getGroupId();

	String getArtifactId();

	Optional<String> getVersion();

	Element getAsDom();

}
