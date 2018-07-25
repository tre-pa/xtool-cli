package br.xtool.core.representation.impl;

import java.nio.file.Path;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Types;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EJavaAnnotation;
import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EJavaMethod;
import br.xtool.core.representation.EJavaPackage;

public class EJavaClassImpl implements EJavaClass {

	protected JavaClassSource javaClassSource;

	private EBootProject project;

	public EJavaClassImpl(EBootProject project, JavaClassSource javaClassSource) {
		super();
		this.project = project;
		this.javaClassSource = javaClassSource;
	}

	/**
	 * Nome da classe
	 * 
	 * @return
	 */
	@Override
	public String getName() {
		return this.javaClassSource.getName();
	}

	/**
	 * Retorna o nome qualificado da classe: pacote+class
	 * 
	 * @return
	 */
	@Override
	public String getQualifiedName() {
		return this.javaClassSource.getQualifiedName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	@Override
	public EJavaPackage getPackage() {
		return EJavaPackageImpl.of(this.javaClassSource.getPackage());
	}

	/**
	 * Verifica se a entidade possui a annotation
	 * 
	 * @param name
	 *            Nome da annotation
	 * @return
	 */
	@Override
	public boolean hasAnnotation(String name) {
		return this.javaClassSource.hasAnnotation(name);
	}

	/**
	 * Retorna o caminho da classe no projeto.
	 * 
	 * @return
	 */
	@Override
	public Path getPath() {
		return this.project.getPath().resolve(String.format("src/main/java/%s/%s.java", this.getPackage().getDir(), this.getName()));
	}

	@Override
	public SortedSet<EJavaField> getFields() {
		// @formatter:off
		return this.javaClassSource.getFields()
				.stream()
				.map(EJavaFieldImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public SortedSet<EJavaAnnotation> getAnnotations() {
		// @formatter:off
		return this.javaClassSource.getAnnotations()
				.stream()
				.map(EJavaAnnotationImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	@Override
	public SortedSet<EJavaMethod> getMethods() {
		// @formatter:off
		return this.javaClassSource.getMethods()
				.stream()
				.map(EJavaMethodImpl::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	/**
	 * Adiciona uma nova annotation a classe
	 * 
	 * @param qualifiedName
	 * @return
	 */
	@Override
	public Optional<EJavaAnnotation> addAnnotation(String qualifiedName) {
		if (StringUtils.isNotBlank(qualifiedName)) {
			if (!this.javaClassSource.hasAnnotation(Types.toSimpleName(qualifiedName))) {
				AnnotationSource<JavaClassSource> annotationSource = this.javaClassSource.addAnnotation();
				this.javaClassSource.addImport(qualifiedName);
				annotationSource.setName(Types.toSimpleName(qualifiedName));
				return Optional.of(new EJavaAnnotationImpl(annotationSource));
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
	@Override
	public Optional<EJavaField> addField(String qualifiedType, String name) {
		if (StringUtils.isNoneBlank(qualifiedType, name)) {
			if (!this.javaClassSource.hasField(name)) {
				FieldSource<JavaClassSource> fieldSource = this.javaClassSource.addField();
				this.javaClassSource.addImport(qualifiedType);
				fieldSource.setName(name);
				fieldSource.setType(Types.toSimpleName(qualifiedType));
				//				Log.print(Log.bold(Log.green("\t[+] ")), Log.purple(" Add: "), Log.white(relativeDestination));
				return Optional.of(new EJavaFieldImpl(fieldSource));
			}
		}
		return Optional.empty();
	}

	/**
	 * Adiciona um import a classe.
	 * 
	 * @param importName
	 */
	@Override
	public void addImport(String importName) {
		if (StringUtils.isNotBlank(importName)) {
			if (!this.javaClassSource.hasImport(importName)) {
				this.javaClassSource.addImport(importName);
			}
		}
	}

	@Override
	public JavaClassSource getRoasterJavaClass() {
		return this.javaClassSource;
	}

	/**
	 * Salva as alteração realizadas no model.
	 */
	//	@Override
	//	public void save() {
	//		try {
	//			FileUtils.writeStringToFile(new File(this.getPath()), this.javaClassSource.toUnformattedString(), "UTF-8");
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//	}

	@Override
	public int compareTo(EJavaClass o) {
		return this.getName().compareTo(o.getName());
	}

}
