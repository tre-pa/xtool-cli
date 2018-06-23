package br.xtool.core.model;

import java.util.List;
import java.util.stream.Stream;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Classe que representa um atributo de classe.
 * 
 * @author jcruz
 *
 */
public class Attribute implements Comparable<Attribute> {

	private SpringBootProject springBootProject;

	private FieldSource<JavaClassSource> fieldSource;

	public Attribute(SpringBootProject springBootProject, FieldSource<JavaClassSource> fieldSource) {
		super();
		this.springBootProject = springBootProject;
		this.fieldSource = fieldSource;
	}

	/**
	 * Retorna o nome do attributo.
	 * 
	 * @return
	 */
	public String getName() {
		return this.fieldSource.getName();
	}

	/**
	 * Retorna as annotation do atributo.
	 * 
	 * @return
	 */
	public List<AnnotationSource<JavaClassSource>> getAnnotations() {
		return this.fieldSource.getAnnotations();
	}

	/**
	 * Retorna o tipo do atributo.
	 * 
	 * @return
	 */
	public Type<JavaClassSource> getType() {
		return this.fieldSource.getType();
	}
	
	public boolean isSingleAssociation() {
		return this.springBootProject.getEntities().parallelStream()
				.anyMatch(entity -> entity.getName().equals(this.getType().getName()));
	}
	
	public boolean isCollectionAssociation() {
		if(Stream.of("List", "Set", "Collection").anyMatch(type -> type.equals(this.getType().getName()))) {
			// @formatter:off
			return this.springBootProject.getEntities().parallelStream()
					.anyMatch(entity -> this.getType().getTypeArguments().stream()
							.anyMatch(t -> t.getName().equals(entity.getName())));
			// @formatter:on
		}
		return false;
	}

	@Override
	public int compareTo(Attribute o) {
		return this.getName().compareTo(o.getName());
	}

}
