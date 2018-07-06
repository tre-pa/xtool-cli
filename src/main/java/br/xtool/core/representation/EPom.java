package br.xtool.core.representation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import br.xtool.core.Log;
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
	private final List<EDependency> dependencies = buildDependencies();

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
		return this.getDependencies().stream().anyMatch(dependency -> dependency.getArtifactId().equals(artifactId));
	}

	/**
	 * Retorna a lista de dependências do projeto.
	 * 
	 * @return
	 */
	private List<EDependency> buildDependencies() {
		List<EDependency> dependencies = new ArrayList<>();
		Element dependenciesNode = this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE);
		for (Element dependency : dependenciesNode.getChildren()) {
			String groupId = dependency.getChild("groupId", NAMESPACE).getTextTrim();
			String artifactId = dependency.getChild("artifactId", NAMESPACE).getTextTrim();
			if (Objects.nonNull(dependency.getChild("version", NAMESPACE))) {
				String version = dependency.getChild("version", NAMESPACE).getTextTrim();
				dependencies.add(new EDependency(groupId, artifactId, version));
				continue;
			}
			dependencies.add(new EDependency(groupId, artifactId));
		}
		return dependencies;
	}

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	public void addDependency(String groupId, String artifactId) {
		EDependency dependency = new EDependency(groupId, artifactId);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
			Log.print(Log.bold(Log.yellow("\t[~] ")), Log.purple("Item: "), Log.white("pom.xml"), Log.gray(" -- "), Log.gray(dependency.toString()));
		}
	}

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	public void addDependency(String groupId, String artifactId, String version) {
		EDependency dependency = new EDependency(groupId, artifactId, version);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
		}
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

	public void save() {
		try (FileOutputStream fos = new FileOutputStream(this.file)) {
			XMLOutputter xmlOutputter = new XMLOutputter();
			Format format = Format.getPrettyFormat();
			format.setIndent("\t");
			xmlOutputter.setFormat(format);
			xmlOutputter.output(this.pomDoc, fos);
			fos.flush();
			fos.close();
			// Log.print(Log.green("\t[UPDATE] ") + Log.white("pom.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
