package br.xtool.mapper.jpa;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.mapper.core.JpaEntityChangeRequest;

@Component
public class AddHibernateDynamicUpdate implements JpaEntityChangeRequest {

	@Override
	public boolean test(JavaClassSource javaClassSource) {
		return !javaClassSource.hasAnnotation("DynamicUpdate");
	}

	@Override
	public void apply(JavaClassSource javaClassSource) {
		javaClassSource.addImport("org.hibernate.annotations.DynamicUpdate");
		javaClassSource.addAnnotation("DynamicUpdate");
	}

}
