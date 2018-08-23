package br.xtool.core.representation.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
import br.xtool.core.representation.EBootPom;
import br.xtool.core.representation.EBootPomDependency;
import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaPackage;
import br.xtool.core.representation.impl.EBootPomDependencyImpl.ScopeType;

public class EBootPomImpl implements EBootPom {

	public static final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	private Path path;

	private Document pomDoc;

	private EBootProject bootProject;

	//	private File file;

	private EBootPomImpl(EBootProject bootProject, Path path) {
		super();
		this.bootProject = bootProject;
		this.path = path;
	}

	@Override
	public EBootProject getProject() {
		return null;
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
	public List<EBootPomDependency> getDependencies() {
		List<EBootPomDependency> dependencies = new ArrayList<>();
		Element dependenciesNode = this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE);
		for (Element dependency : dependenciesNode.getChildren()) {
			String groupId = dependency.getChild("groupId", NAMESPACE).getTextTrim();
			String artifactId = dependency.getChild("artifactId", NAMESPACE).getTextTrim();
			if (Objects.nonNull(dependency.getChild("version", NAMESPACE))) {
				String version = dependency.getChild("version", NAMESPACE).getTextTrim();
				dependencies.add(new EBootPomDependencyImpl(groupId, artifactId, version));
				continue;
			}
			dependencies.add(new EBootPomDependencyImpl(groupId, artifactId));
		}
		return dependencies;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#addDependency(java.lang.String, java.lang.String)
	 */
	@Override
	public EBootPom addDependency(String groupId, String artifactId) {
		EBootPomDependencyImpl dependency = new EBootPomDependencyImpl(groupId, artifactId);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
			ConsoleLog.print(ConsoleLog.bold(ConsoleLog.yellow("\t[~] ")), ConsoleLog.purple("Item: "), ConsoleLog.white("pom.xml"), ConsoleLog.gray(" -- "), ConsoleLog.gray(dependency.toString()));
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.ESBootPom#addDependency(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public EBootPom addDependency(String groupId, String artifactId, String version) {
		EBootPomDependencyImpl dependency = new EBootPomDependencyImpl(groupId, artifactId, version);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
		}
		return this;
	}

	@Override
	public EBootPom addDependency(String groupId, String artifactId, ScopeType scopeType) {
		EBootPomDependencyImpl dependency = new EBootPomDependencyImpl(groupId, artifactId, scopeType);
		if (!hasArtifactId(dependency.getArtifactId())) {
			this.pomDoc.getRootElement().getChild("dependencies", NAMESPACE).addContent(dependency.getAsDom());
		}
		return this;
	}

	public static EBootPomImpl of(EBootProject bootProject, Path path) {
		if (Files.exists(path)) {
			try {
				EBootPomImpl pomRepresentation = new EBootPomImpl(bootProject, path);
				SAXBuilder saxBuilder = new SAXBuilder();
				pomRepresentation.pomDoc = saxBuilder.build(Files.newBufferedReader(path));
				return pomRepresentation;
			} catch (JDOMException | IOException e) {
				e.printStackTrace();
			}
		}
		throw new IllegalArgumentException("Não foi possível localizar ou ler o arquivo pom.xml. Verifique se o mesmo existe o contém erros.");
	}

	@Override
	public void save() {
		try (BufferedWriter bw = Files.newBufferedWriter(this.path)) {
			XMLOutputter xmlOutputter = new XMLOutputter();
			Format format = Format.getPrettyFormat();
			format.setIndent("\t");
			xmlOutputter.setFormat(format);
			xmlOutputter.output(this.pomDoc, bw);
			bw.flush();
			bw.close();
			// Log.print(Log.green("\t[UPDATE] ") + Log.white("pom.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
