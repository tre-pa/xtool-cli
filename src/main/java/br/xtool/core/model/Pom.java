package br.xtool.core.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Representa o arquivo pom.xml
 * 
 * @author jcruz
 *
 */
public class Pom {

	private final Namespace NAMESPACE = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");

	private String path;

	private Document pomDoc;

	private List<Dependency> dependencies;

	public Pom(String path) throws JDOMException, IOException {
		super();
		this.path = path;
		File file = new File(FilenameUtils.concat(path, "pom.xml"));
		SAXBuilder saxBuilder = new SAXBuilder();
		this.pomDoc = saxBuilder.build(file);
	}

	public Package getGroupId() {
		return Package.of(pomDoc.getRootElement().getChild("groupId", NAMESPACE).getText());
	}

	public List<Dependency> getDependencies() {
		if (dependencies == null) {
			this.dependencies = new ArrayList<>();
			Element dependencies = pomDoc.getRootElement().getChild("dependencies", NAMESPACE);
			for (Element dependency : dependencies.getChildren()) {
				String groupId = dependency.getChild("groupId", NAMESPACE).getTextTrim();
				String artifactId = dependency.getChild("artifactId", NAMESPACE).getTextTrim();
				String version = dependency.getChild("version", NAMESPACE) == null ? null : dependency.getChild("version", NAMESPACE).getTextTrim();
				this.dependencies.add(new Dependency(groupId, artifactId, version));
			}
		}
		return this.dependencies;
	}

	@Getter
	@ToString
	@EqualsAndHashCode
	public class Dependency {
		private String groupId;

		private String artifactId;

		private String version;

		public Dependency(String groupId, String artifactId, String version) {
			super();
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}

	}

}
