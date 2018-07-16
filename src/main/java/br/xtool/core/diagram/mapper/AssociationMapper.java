package br.xtool.core.diagram.mapper;

import java.util.Map;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import net.sourceforge.plantuml.cucadiagram.Link;

@FunctionalInterface
public interface AssociationMapper {

	public void map(Map<String, JavaClassSource> javaClasses, Link link);

	default public JavaClassSource getJavaClassSource(Map<String, JavaClassSource> javaClasses, Link link) {
		return javaClasses.get(link.getEntity1().getDisplay().asStringWithHiddenNewLine());
	}

	default public JavaClassSource getJavaClassTarget(Map<String, JavaClassSource> javaClasses, Link link) {
		return javaClasses.get(link.getEntity2().getDisplay().asStringWithHiddenNewLine());
	}

}
