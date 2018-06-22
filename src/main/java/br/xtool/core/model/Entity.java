package br.xtool.core.model;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um entidade JPA
 * 
 * @author jcruz
 *
 */
public class Entity implements Comparable<Entity> {

	private JavaClassSource javaClassSource;

	private SortedSet<Attribute> attributes;

	public Entity(JavaClassSource javaClassSource) {
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
	public Package getPackage() {
		return Package.of(javaClassSource.getPackage());
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
	 * Retorna os atributos da classe.
	 * 
	 * @return
	 */
	public SortedSet<Attribute> getAttributes() {
		if (this.attributes == null) {
			// @formatter:off
			this.attributes = this.javaClassSource.getFields().stream()
					.map(Attribute::new)
					.collect(Collectors.toCollection(TreeSet::new));
			// @formatter:on
		}
		return this.attributes;
	}

	@Override
	public int compareTo(Entity o) {
		return this.getName().compareTo(o.getName());
	}

}
