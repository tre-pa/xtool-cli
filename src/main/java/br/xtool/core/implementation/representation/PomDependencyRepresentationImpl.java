package br.xtool.core.implementation.representation;

import java.util.Optional;

import org.jdom2.Element;

import br.xtool.core.representation.springboot.PomDependencyRepresentation;
import br.xtool.core.representation.springboot.PomRepresentation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Classe que representa uma depêndencia do pom.xml
 * 
 * @author jcruz
 *
 */
@EqualsAndHashCode
public class PomDependencyRepresentationImpl implements PomDependencyRepresentation {
	private String groupId;

	private String artifactId;

	private String version;

	private ScopeType scope = ScopeType.COMPILE;

	@AllArgsConstructor
	@Getter
	public enum ScopeType {
		COMPILE(""), TEST("test"), IMPORT("import"), SYSTEM("system"), PROVIDED("provided"), RUNTIME("runtime");
		private String scope;
	}

	public PomDependencyRepresentationImpl(String groupId, String artifactId, String version, ScopeType scope) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.scope = scope;
	}

	public PomDependencyRepresentationImpl(String groupId, String artifactId, ScopeType scope) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.scope = scope;
	}

	public PomDependencyRepresentationImpl(String groupId, String artifactId, String version) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

	public PomDependencyRepresentationImpl(String groupId, String artifactId) {
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
		Element dependency = new Element("dependency", PomRepresentation.NAMESPACE);
		this.buildGroupId(dependency);
		this.buildArtifiactId(dependency);
		this.buildVersion(dependency);
		this.buildScope(dependency);
		return dependency;
	}

	private void buildGroupId(Element dependency) {
		Element groupId = new Element("groupId", PomRepresentation.NAMESPACE);
		groupId.setText(this.getGroupId());
		dependency.addContent(groupId);
	}

	private void buildArtifiactId(Element dependency) {
		Element artifactId = new Element("artifactId", PomRepresentation.NAMESPACE);
		artifactId.setText(this.getArtifactId());
		dependency.addContent(artifactId);
	}

	private void buildVersion(Element dependency) {
		if (this.getVersion().isPresent()) {
			Element version = new Element("version", PomRepresentation.NAMESPACE);
			version.setText(this.getVersion().get());
			dependency.addContent(version);
		}
	}

	private void buildScope(Element dependency) {
		if (!this.getScope().equals(ScopeType.COMPILE)) {
			Element scope = new Element("scope", PomRepresentation.NAMESPACE);
			scope.setText(this.getScope().getScope());
			dependency.addContent(scope);
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

	public ScopeType getScope() {
		return this.scope;
	}

	@Override
	public String toString() {
		return "Dependency [" + (this.groupId != null ? "groupId=" + this.groupId + ", " : "") + (this.artifactId != null ? "artifactId=" + this.artifactId : "")
				+ (this.version != null ? ",version=" + this.version : "") + "]";
	}

}
