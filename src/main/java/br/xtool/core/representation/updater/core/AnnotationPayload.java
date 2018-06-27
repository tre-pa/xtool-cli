package br.xtool.core.representation.updater.core;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EntityRepresentation;

public interface AnnotationPayload {

	public void apply(EntityRepresentation representation, AnnotationSource<JavaClassSource> annotationSource);

}
