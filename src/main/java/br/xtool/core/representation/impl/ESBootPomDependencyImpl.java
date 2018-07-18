package br.xtool.core.representation.impl;

import java.util.Optional;

import org.jdom2.Element;

import br.xtool.core.representation.ESBootPomDependency;
import br.xtool.core.representation.ESBootPom;
import lombok.EqualsAndHashCode;

/**
 * Classe que representa uma depêndencia do pom.xml
 * 
 * @author jcruz
 *
 */
@EqualsAndHashCode
public class ESBootPomDependencyImpl implements ESBootPomDependency {
	private String groupId;

	private String artifactId;

	private String version;

	public ESBootPomDependencyImpl(String groupId, String artifactId, String version) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

	public ESBootPomDependencyImpl(String groupId, String artifactId) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
	}

	/**
	 * Retorna o objeto Dependency com um objeto JDOM
	 * 
	 * @return
	 */
	@Override
	public Element getAsDom() {
		Element dependency = new Element("dependency", ESBootPom.NAMESPACE);
		this.buildGroupId(dependency);
		this.buildArtifiactId(dependency);
		this.buildVersion(dependency);
		return dependency;
	}

	private void buildGroupId(Element dependency) {
		Element groupId = new Element("groupId", ESBootPom.NAMESPACE);
		groupId.setText(this.getGroupId());
		dependency.addContent(groupId);
	}

	private void buildArtifiactId(Element dependency) {
		Element artifactId = new Element("artifactId", ESBootPom.NAMESPACE);
		artifactId.setText(this.getArtifactId());
		dependency.addContent(artifactId);
	}

	private void buildVersion(Element dependency) {
		if (this.getVersion().isPresent()) {
			Element version = new Element("version", ESBootPom.NAMESPACE);
			version.setText(this.getVersion().get());
			dependency.addContent(version);
		}
	}

	@Override
	public String getGroupId() {
		return this.groupId;
	}

	@Override
	public String getArtifactId() {
		return this.artifactId;
	}

	@Override
	public Optional<String> getVersion() {
		return Optional.ofNullable(this.version);
	}

	@Override
	public String toString() {
		return "Dependency [" + (this.groupId != null ? "groupId=" + this.groupId + ", " : "") + (this.artifactId != null ? "artifactId=" + this.artifactId : "")
				+ (this.version != null ? ",version=" + this.version : "") + "]";
	}
}
