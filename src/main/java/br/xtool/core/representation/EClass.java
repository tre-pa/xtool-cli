package br.xtool.core.representation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import lombok.Getter;

public class EClass {

	protected JavaClassSource javaClassSource;

	private EProject project;

	@Getter(lazy = true)
	private final SortedSet<EField> fields = buildFields();

	@Getter(lazy = true)
	private final SortedSet<EAnnotation> annotations = buildAnnotations();

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
		return javaClassSource.getName();
	}

	/**
	 * Retorna o nome qualificado da classe: pacote+class
	 * 
	 * @return
	 */
	public String getQualifiedName() {
		return javaClassSource.getQualifiedName();
	}

	/**
	 * Retorna o pacote da classe
	 * 
	 * @return
	 */
	public EPackage getPackage() {
		return EPackage.of(javaClassSource.getPackage());
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

	private SortedSet<EField> buildFields() {
		// @formatter:off
		return this.javaClassSource.getFields()
				.stream()
				.map(fieldSource -> new EField(this, fieldSource))
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

	private SortedSet<EAnnotation> buildAnnotations() {
		// @formatter:off
		return this.javaClassSource.getAnnotations()
				.stream()
				.map(EAnnotation::new)
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
			String[] annotationTokens = StringUtils.split(qualifiedName, ".");
			String annotationName = annotationTokens[annotationTokens.length - 1];
			if (!javaClassSource.hasAnnotation(annotationName)) {
				AnnotationSource<JavaClassSource> annotationSource = this.javaClassSource.addAnnotation();
				this.javaClassSource.addImport(qualifiedName);
				annotationSource.setName(annotationName);
				return Optional.of(new EAnnotation(annotationSource));
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param name
	 */
	public void addImport(String name) {
		if (!this.javaClassSource.hasImport(name)) {
			this.javaClassSource.addImport(name);
		}
	}

	/**
	 * Salva as alteração realizadas no model.
	 */
	public void save() {
		try (FileWriter fw = new FileWriter(new File(this.getPath()))) {
			fw.write(this.javaClassSource.toUnformattedString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o caminho da classe no projeto.
	 * 
	 * @return
	 */
	public String getPath() {
		return FilenameUtils.concat(this.project.getPath(), String.format("src/main/java/%s/%s.java", this.getPackage().getDir(), this.getName()));
	}

}
