package br.xtool.mapper.core;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public interface AttributeChangeRequest {

	public boolean test(JavaClassSource javaClassSource, FieldSource<JavaClassSource> fieldSource);

	public boolean apply(JavaClassSource javaClassSource, FieldSource<JavaClassSource> fieldSource);

}
