package br.xtool.core.representation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import br.xtool.core.Log;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Representa o arquivo pom.xml
 * 
 * @author jcruz
 *
 */
public class PomRepresentation {

	public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	private Document pomDoc;

	private List<Dependency> dependencies;

	private File file;

	private Element rootElement;
	
	private List<String> updateInfo = new ArrayList<>();

	public PomRepresentation(String path) throws JDOMException, IOException {
		super();
		this.file = new File(path);
		SAXBuilder saxBuilder = new SAXBuilder();
		this.pomDoc = saxBuilder.build(file);
		this.rootElement = this.pomDoc.getRootElement();
	}

	/**
	 * Retorna o groupId da aplicação.
	 * 
	 * @return
	 */
	public PackageRepresentation getGroupId() {
		return PackageRepresentation.of(this.rootElement.getChild("groupId", NAMESPACE).getText());
	}

	/**
	 * Retorna a versão da aplicação.
	 * 
	 * @return
	 */
	public String getVersion() {
		return this.rootElement.getChild("version", NAMESPACE).getText();
	}

	/**
	 * Retorna a versão do parent.
	 * 
	 * @return
	 */
	public String getParentVersion() {
		return this.rootElement.getChild("parent", NAMESPACE).getChild("version", NAMESPACE).getText();
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
	public List<Dependency> getDependencies() {
		if (dependencies == null) {
			this.dependencies = new ArrayList<>();
			Element dependencies = this.rootElement.getChild("dependencies", NAMESPACE);
			for (Element dependency : dependencies.getChildren()) {
				String groupId = dependency.getChild("groupId", NAMESPACE).getTextTrim();
				String artifactId = dependency.getChild("artifactId", NAMESPACE).getTextTrim();
				if (Objects.nonNull(dependency.getChild("version", NAMESPACE))) {
					String version = dependency.getChild("version", NAMESPACE).getTextTrim();
					this.dependencies.add(new Dependency(groupId, artifactId, version));
					continue;
				}
				this.dependencies.add(new Dependency(groupId, artifactId));
			}
		}
		return this.dependencies;
	}

	/**
	 * Adciona uma dependência o pom.xml
	 * 
	 * @param dependency
	 */
	@Deprecated
	public void addDependency(Dependency dependency) {
		this.rootElement.getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
		this.updateInfo.add("\t\t + " + dependency);
	}

	/**
	 * Comita as alterações no pom.xml
	 * 
	 * @throws IOException
	 */
	@Deprecated
	public void commitUpdate() throws IOException {
		try (FileOutputStream fos = new FileOutputStream(this.file)) {
			XMLOutputter xmlOutputter = new XMLOutputter();
			Format format = Format.getPrettyFormat();
			format.setIndent("\t");
			xmlOutputter.setFormat(format);
			xmlOutputter.output(this.pomDoc, fos);
			fos.flush();
			fos.close();
			Log.print(Log.green("\t[UPDATE] ") + Log.white("pom.xml"));
			this.updateInfo.forEach(info -> Log.print(info));
			this.updateInfo.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
			Element dependency = new Element("dependency", PomRepresentation.NAMESPACE);
			this.addGroupId(dependency);
			this.addArtifiactId(dependency);
			this.addVersion(dependency);
			return dependency;
		}

		private void addGroupId(Element dependency) {
			Element groupId = new Element("groupId", PomRepresentation.NAMESPACE);
			groupId.setText(this.getGroupId());
			dependency.addContent(groupId);
		}

		private void addArtifiactId(Element dependency) {
			Element artifactId = new Element("artifactId", PomRepresentation.NAMESPACE);
			artifactId.setText(this.getArtifactId());
			dependency.addContent(artifactId);
		}

		private void addVersion(Element dependency) {
			if (StringUtils.isNoneBlank(this.getVersion())) {
				Element version = new Element("version", PomRepresentation.NAMESPACE);
				version.setText(this.getVersion());
				dependency.addContent(version);
			}
		}

		@Override
		public String toString() {
			return "Dependency [" + (groupId != null ? "groupId=" + groupId + ", " : "") + (artifactId != null ? "artifactId=" + artifactId + ", " : "")
					+ (version != null ? "version=" + version : "") + "]";
		}

	}

}
