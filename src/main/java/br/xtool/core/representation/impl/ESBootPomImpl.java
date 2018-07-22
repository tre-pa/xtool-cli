package br.xtool.core.representation.impl;

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

import br.xtool.core.ConsoleLog;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.ESBootPom;
import br.xtool.core.representation.ESBootPomDependency;

public class ESBootPomImpl implements ESBootPom {

	public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	private Document pomDoc;

	private File file;

	private ESBootPomImpl(String path) {
		super();

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#getGroupId()
	 */
	@Override
	public EJavaPackage getGroupId() {
		return EJavaPackageImpl.of(this.pomDoc.getRootElement().getChild("groupId", NAMESPACE).getText());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#getVersion()
	 */
	@Override
	public String getVersion() {
		return this.pomDoc.getRootElement().getChild("version", NAMESPACE).getText();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#getParentGroupId()
	 */
	@Override
	public Optional<String> getParentGroupId() {
		if (Objects.nonNull(this.pomDoc.getRootElement().getChild("parent", NAMESPACE))) {
			return Optional.ofNullable(this.pomDoc.getRootElement().getChild("parent", NAMESPACE).getChild("groupId", NAMESPACE).getText());
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#getParentVersion()
	 */
	@Override
	public Optional<String> getParentVersion() {
		if (Objects.nonNull(this.pomDoc.getRootElement().getChild("parent", NAMESPACE))) {
			return Optional.ofNullable(this.pomDoc.getRootElement().getChild("parent", NAMESPACE).getChild("version", NAMESPACE).getText());
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#hasArtifactId(java.lang.String)
	 */
	@Override
	public boolean hasArtifactId(String artifactId) {
		return this.getDependencies().stream().anyMatch(dependency -> dependency.getArtifactId().equals(artifactId));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#getDependencies()
	 */
	@Override
	public List<ESBootPomDependency> getDependencies() {
		List<ESBootPomDependency> dependencies = new ArrayList<>();
		Element dependenciesNode = this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE);
		for (Element dependency : dependenciesNode.getChildren()) {
			String groupId = dependency.getChild("groupId", NAMESPACE).getTextTrim();
			String artifactId = dependency.getChild("artifactId", NAMESPACE).getTextTrim();
			if (Objects.nonNull(dependency.getChild("version", NAMESPACE))) {
				String version = dependency.getChild("version", NAMESPACE).getTextTrim();
				dependencies.add(new ESBootPomDependencyImpl(groupId, artifactId, version));
				continue;
			}
			dependencies.add(new ESBootPomDependencyImpl(groupId, artifactId));
		}
		return dependencies;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#addDependency(java.lang.String, java.lang.String)
	 */
	@Override
	public void addDependency(String groupId, String artifactId) {
		ESBootPomDependencyImpl dependency = new ESBootPomDependencyImpl(groupId, artifactId);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
			ConsoleLog.print(ConsoleLog.bold(ConsoleLog.yellow("\t[~] ")), ConsoleLog.purple("Item: "), ConsoleLog.white("pom.xml"), ConsoleLog.gray(" -- "), ConsoleLog.gray(dependency.toString()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#addDependency(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addDependency(String groupId, String artifactId, String version) {
		ESBootPomDependencyImpl dependency = new ESBootPomDependencyImpl(groupId, artifactId, version);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
		}
	}

	public static ESBootPomImpl of(String path) {
		if (Files.exists(Paths.get(path))) {
			try {
				ESBootPomImpl pomRepresentation = new ESBootPomImpl(path);
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
