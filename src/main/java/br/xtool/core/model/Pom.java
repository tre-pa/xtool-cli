package br.xtool.core.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class Pom {

	private Document pomDoc;

	public Pom(String path) throws JDOMException, IOException {
		super();
		File file = new File(FilenameUtils.concat(path, "pom.xml"));
		SAXBuilder saxBuilder = new SAXBuilder();
		this.pomDoc = saxBuilder.build(file);
	}

	public String getGroupId() {
		return pomDoc.getRootElement().getChild("groupId", Namespace.getNamespace("http://maven.apache.org/POM/4.0.0")).getText();
	}

	public String getGroupAsDir() {
		return pomDoc.getRootElement().getChild("groupId", Namespace.getNamespace("http://maven.apache.org/POM/4.0.0")).getText().replaceAll("\\.", "/");
	}

}
