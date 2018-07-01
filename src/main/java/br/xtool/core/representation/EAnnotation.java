package br.xtool.core.representation;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 * Representação de uma annotation.
 * 
 * @author jcruz
 *
 */
public class EAnnotation implements Comparable<EAnnotation> {

	private AnnotationSource<JavaClassSource> annotation;

	public EAnnotation(AnnotationSource<JavaClassSource> annotation) {
		super();
		this.annotation = annotation;
	}

	/**
	 * Retorna o nome da annotation.
	 * 
	 * @return
	 */
	public String getName() {
		return annotation.getName();
	}

	@Override
	public int compareTo(EAnnotation o) {
		return this.getName().compareTo(o.getName());
	}

}
