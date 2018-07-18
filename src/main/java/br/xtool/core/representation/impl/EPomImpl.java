package br.xtool.core.representation.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.EDependency;
import br.xtool.core.representation.EPackage;
import br.xtool.core.representation.EPom;

/**
 * Representa o arquivo pom.xml
 * 
 * @author jcruz
 *
 */
public class EPomImpl implements EPom {

	public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	private Document pomDoc;

	private File file;

	private EPomImpl(String path) {
		super();

	}

	/**
	 * Retorna o groupId da aplicação.
	 * 
	 * @return
	 */
	@Override
	public EPackage getGroupId() {
		return EPackageImpl.of(this.pomDoc.getRootElement().getChild("groupId", NAMESPACE).getText());
	}

	/**
	 * Retorna a versão da aplicação.
	 * 
	 * @return
	 */
	@Override
	public String getVersion() {
		return this.pomDoc.getRootElement().getChild("version", NAMESPACE).getText();
	}

	/**
	 * Retorna a versão do parent.
	 * 
	 * @return
	 */
	@Override
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
	@Override
	public boolean hasArtifactId(String artifactId) {
		return this.getDependencies().stream().anyMatch(dependency -> dependency.getArtifactId().equals(artifactId));
	}

	/**
	 * Retorna a lista de dependências do projeto.
	 * 
	 * @return
	 */
	@Override
	public List<EDependency> getDependencies() {
		List<EDependency> dependencies = new ArrayList<>();
		Element dependenciesNode = this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE);
		for (Element dependency : dependenciesNode.getChildren()) {
			String groupId = dependency.getChild("groupId", NAMESPACE).getTextTrim();
			String artifactId = dependency.getChild("artifactId", NAMESPACE).getTextTrim();
			if (Objects.nonNull(dependency.getChild("version", NAMESPACE))) {
				String version = dependency.getChild("version", NAMESPACE).getTextTrim();
				dependencies.add(new EDependencyImpl(groupId, artifactId, version));
				continue;
			}
			dependencies.add(new EDependencyImpl(groupId, artifactId));
		}
		return dependencies;
	}

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	@Override
	public void addDependency(String groupId, String artifactId) {
		EDependencyImpl dependency = new EDependencyImpl(groupId, artifactId);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
			ConsoleLog.print(ConsoleLog.bold(ConsoleLog.yellow("\t[~] ")), ConsoleLog.purple("Item: "), ConsoleLog.white("pom.xml"), ConsoleLog.gray(" -- "), ConsoleLog.gray(dependency.toString()));
		}
	}

	/**
	 * Adciona uma dependência o pom.xml caso não exista.
	 * 
	 * @param dependency
	 */
	@Override
	public void addDependency(String groupId, String artifactId, String version) {
		EDependencyImpl dependency = new EDependencyImpl(groupId, artifactId, version);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
		}
	}

	public static EPomImpl of(String path) {
		if (Files.exists(Paths.get(path))) {
			try {
				EPomImpl pomRepresentation = new EPomImpl(path);
				pomRepresentation.file = new File(path);
				SAXBuilder saxBuilder = new SAXBuilder();
				pomRepresentation.pomDoc = saxBuilder.build(pomRepresentation.file);
				return pomRepresentation;
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		throw new IllegalArgumentException("Não foi possível localizar ou ler o arquivo pom.xml. Verifique se o mesmo existe o contém erros.");
	}

	@Override
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
