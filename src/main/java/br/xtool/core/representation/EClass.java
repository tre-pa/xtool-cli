package br.xtool.core.representation;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Types;

public class EClass {

	protected JavaClassSource javaClassSource;

	private EProject project;

	public EClass(EProject project, JavaClassSource javaClassSource) {
		super();
		this.project = project;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Nome da classe
	 * 
	 * @return
	 */
	public String getName() {
		return this.javaClassSource.getName();
	}

	/**
	 * Retorna o nome qualificado da classe: pacote+class
	 * 
	 * @return
	 */
	public String getQualifiedName() {
		return this.javaClassSource.getQualifiedName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	public EPackage getPackage() {
		return EPackage.of(this.javaClassSource.getPackage());
	}

	/**
	 * Verifica se a entidade possui a annotation
	 * 
	 * @param name
	 *            Nome da annotation
	 * @return
	 */
	public boolean hasAnnotation(String name) {
		return this.javaClassSource.hasAnnotation(name);
	}

	/**
	 * Retorna o caminho da classe no projeto.
	 * 
	 * @return
	 */
	public String getPath() {
		return FilenameUtils.concat(this.project.getPath(), String.format("src/main/java/%s/%s.java", this.getPackage().getDir(), this.getName()));
	}

	public SortedSet<EField> getFields() {
		// @formatter:off
		return this.javaClassSource.getFields()
				.stream()
				.map(EField::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	public SortedSet<EAnnotation> getAnnotations() {
		// @formatter:off
		return this.javaClassSource.getAnnotations()
				.stream()
				.map(EAnnotation::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	public SortedSet<EMethod> getMethods() {
		// @formatter:off
		return this.javaClassSource.getMethods()
				.stream()
				.map(EMethod::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Adiciona uma nova annotation a classe
	 * 
	 * @param qualifiedName
	 * @return
	 */
	public Optional<EAnnotation> addAnnotation(String qualifiedName) {
		if (StringUtils.isNotBlank(qualifiedName)) {
			if (!this.javaClassSource.hasAnnotation(Types.toSimpleName(qualifiedName))) {
				AnnotationSource<JavaClassSource> annotationSource = this.javaClassSource.addAnnotation();
				this.javaClassSource.addImport(qualifiedName);
				annotationSource.setName(Types.toSimpleName(qualifiedName));
				return Optional.of(new EAnnotation(annotationSource));
			}
		}
		return Optional.empty();
	}

	/**
	 * Adiciona um nova atributo a classe.
	 * 
	 * @param qualifiedType
	 * @param name
	 * @return
	 */
	public Optional<EField> addField(String qualifiedType, String name) {
		if (StringUtils.isNoneBlank(qualifiedType, name)) {
			if (!this.javaClassSource.hasField(name)) {
				FieldSource<JavaClassSource> fieldSource = this.javaClassSource.addField();
				this.javaClassSource.addImport(qualifiedType);
				fieldSource.setName(name);
				fieldSource.setType(Types.toSimpleName(qualifiedType));
				//				Log.print(Log.bold(Log.green("\t[+] ")), Log.purple(" Add: "), Log.white(relativeDestination));
				return Optional.of(new EField(fieldSource));
			}
		}
		return Optional.empty();
	}

	/**
	 * Adiciona um import a classe.
	 * 
	 * @param importName
	 */
	public void addImport(String importName) {
		if (StringUtils.isNotBlank(importName)) {
			if (!this.javaClassSource.hasImport(importName)) {
				this.javaClassSource.addImport(importName);
			}
		}
	}

	/**
	 * Salva as alteração realizadas no model.
	 */
	public void save() {
		try {
			FileUtils.writeStringToFile(new File(this.getPath()), this.javaClassSource.toUnformattedString(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
