package br.xtool.core.representation;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Classe que representa uma depêndencia do pom.xml
 * 
 * @author jcruz
 *
 */
@Getter
@EqualsAndHashCode
public class EDependency {
	private String groupId;

	private String artifactId;

	private String version;

	public EDependency(String groupId, String artifactId, String version) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

	public EDependency(String groupId, String artifactId) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
	}

	/**
	 * Retorna o objeto Dependency com um objeto JDOM
	 * 
	 * @return
	 */
	public Element getAsDom() {
		Element dependency = new Element("dependency", EPom.NAMESPACE);
		this.buildGroupId(dependency);
		this.buildArtifiactId(dependency);
		this.buildVersion(dependency);
		return dependency;
	}

	private void buildGroupId(Element dependency) {
		Element groupId = new Element("groupId", EPom.NAMESPACE);
		groupId.setText(this.getGroupId());
		dependency.addContent(groupId);
	}

	private void buildArtifiactId(Element dependency) {
		Element artifactId = new Element("artifactId", EPom.NAMESPACE);
		artifactId.setText(this.getArtifactId());
		dependency.addContent(artifactId);
	}

	private void buildVersion(Element dependency) {
		if (StringUtils.isNoneBlank(this.getVersion())) {
			Element version = new Element("version", EPom.NAMESPACE);
			version.setText(this.getVersion());
			dependency.addContent(version);
		}
	}

	@Override
	public String toString() {
		return "Dependency [" + (groupId != null ? "groupId=" + groupId + ", " : "") + (artifactId != null ? "artifactId=" + artifactId : "") + (version != null ? ",version=" + version : "") + "]";
	}

}
