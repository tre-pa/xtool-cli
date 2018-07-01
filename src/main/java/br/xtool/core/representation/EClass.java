package br.xtool.core.representation;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import lombok.Getter;

public class EClass {

	protected JavaClassSource javaClassSource;

	@Getter(lazy = true)
	private final SortedSet<EField> fields = buildFields();

	public EClass(JavaClassSource javaClassSource) {
		super();
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
	 * Retorna as annotations da classe
	 * 
	 * @return
	 */
	public List<AnnotationSource<JavaClassSource>> getAnnotations() {
		return this.javaClassSource.getAnnotations();
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
				.map(EField::new)
				.collect(Collectors.toCollection(TreeSet::new));
		// @formatter:on
	}

}
