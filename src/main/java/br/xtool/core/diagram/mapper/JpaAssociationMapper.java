package br.xtool.core.diagram.mapper;

import java.util.Map;
import java.util.regex.Pattern;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkType;

@FunctionalInterface
public interface JpaAssociationMapper {

	public Pattern manyMultiplicityPattern = Pattern.compile("\\*|(\\d\\.\\.\\*)");

	public Pattern oneMultiplicityPattern = Pattern.compile("(0\\.\\.1)|(1\\.\\.1)|1");

	public void map(Map<String, JavaClassSource> javaClasses, Link link);

	default public JavaClassSource getJavaClassSource(Map<String, JavaClassSource> javaClasses, Link link) {
		return javaClasses.get(link.getEntity1().getDisplay().asStringWithHiddenNewLine());
	}

	default public JavaClassSource getJavaClassTarget(Map<String, JavaClassSource> javaClasses, Link link) {
		return javaClasses.get(link.getEntity2().getDisplay().asStringWithHiddenNewLine());
	}

	default String getSourceQualifier(Link link) {
		return link.getQualifier1();
	}

	default String getTargetQualifier(Link link) {
		return link.getQualifier2();
	}

	default LinkType getAssociationType(Link link) {
		return link.getType();
	}

}
