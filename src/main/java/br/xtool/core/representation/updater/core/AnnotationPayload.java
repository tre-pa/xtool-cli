package br.xtool.core.representation.updater.core;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EEntity;

public interface AnnotationPayload {

	public void apply(EEntity representation, AnnotationSource<JavaClassSource> annotationSource);

}
