package br.xtool.core.representation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Representa o arquivo pom.xml
 * 
 * @author jcruz
 *
 */
public class EPom {

	public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	private Document pomDoc;

	@Getter(lazy = true)
	private final List<Dependency> dependencies = buildDependencies();

	private File file;

	private EPom(String path) {
		super();

	}

	/**
	 * Retorna o groupId da aplicação.
	 * 
	 * @return
	 */
	public EPackage getGroupId() {
		return EPackage.of(this.pomDoc.getRootElement().getChild("groupId", NAMESPACE).getText());
	}

	/**
	 * Retorna a versão da aplicação.
	 * 
	 * @return
	 */
	public String getVersion() {
		return this.pomDoc.getRootElement().getChild("version", NAMESPACE).getText();
	}

	/**
	 * Retorna a versão do parent.
	 * 
	 * @return
	 */
	public String getParentVersion() {
		return this.pomDoc.getRootElement().getChild("parent", NAMESPACE).getChild("version", NAMESPACE).getText();
	}

	/**
	 * Verifica se um atefato existe no pom.xml
	 * 
	 * @param artifactId
	 *            Nome do artefato
	 * @return
	 */
	public boolean hasArtifactId(String artifactId) {
		// @formatter:off
		return this.getDependencies().stream()
				.anyMatch(dependency -> dependency.getArtifactId().equals(artifactId));
		// @formatter:on
	}

	/**
	 * Retorna a lista de dependências do projeto.
	 * 
	 * @return
	 */
	private List<Dependency> buildDependencies() {
		List<Dependency> dependencies = new ArrayList<>();
		Element dependenciesNode = this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE);
		for (Element dependency : dependenciesNode.getChildren()) {
			String groupId = dependency.getChild("groupId", NAMESPACE).getTextTrim();
			String artifactId = dependency.getChild("artifactId", NAMESPACE).getTextTrim();
			if (Objects.nonNull(dependency.getChild("version", NAMESPACE))) {
				String version = dependency.getChild("version", NAMESPACE).getTextTrim();
				dependencies.add(new Dependency(groupId, artifactId, version));
				continue;
			}
			dependencies.add(new Dependency(groupId, artifactId));
		}
		return dependencies;
	}

	public static Optional<EPom> of(String path) {
		if (Files.exists(Paths.get(path))) {
			try {
				EPom pomRepresentation = new EPom(path);
				pomRepresentation.file = new File(path);
				SAXBuilder saxBuilder = new SAXBuilder();
				pomRepresentation.pomDoc = saxBuilder.build(pomRepresentation.file);
				return Optional.of(pomRepresentation);
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		return Optional.empty();
	}

	/**
	 * Classe que representa uma depêndencia do pom.xml
	 * 
	 * @author jcruz
	 *
	 */
	@Getter
	@EqualsAndHashCode
	public static class Dependency {
		private String groupId;

		private String artifactId;

		private String version;

		public Dependency(String groupId, String artifactId, String version) {
			super();
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}

		public Dependency(String groupId, String artifactId) {
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
			return "Dependency [" + (groupId != null ? "groupId=" + groupId + ", " : "") + (artifactId != null ? "artifactId=" + artifactId + ", " : "") + (version != null ? "version=" + version : "")
					+ "]";
		}

	}

}
