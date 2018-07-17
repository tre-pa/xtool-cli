package br.xtool.core.diagram.mapper.association;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.AssociationMapper;
import br.xtool.core.util.Inflector;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkArrow;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import strman.Strman;

/**
 * Mapeia as associações OneToMany bidirecionais.
 * 
 * @author jcruz
 *
 */
@Component
public class ManyToManyBidAssociationMapper implements AssociationMapper {

	@Override
	public void map(Map<String, JavaClassSource> javaClasses, Link link) {
		if (this.getAssociationType(link).getDecor1().equals(LinkDecor.ARROW) && this.getAssociationType(link).getDecor2().equals(LinkDecor.ARROW)) {
			String targetQualifier = this.getTargetQualifier(link);
			String sourceQualifier = this.getSourceQualifier(link);
			if (manyMultiplicityPattern.matcher(targetQualifier).matches() && manyMultiplicityPattern.matcher(sourceQualifier).matches()) {
				JavaClassSource sourceJavaClass = this.getJavaClassSource(javaClasses, link);
				JavaClassSource targetJavaClass = this.getJavaClassTarget(javaClasses, link);
				mapJavaTarget(sourceJavaClass, targetJavaClass, link);
				mapJavaSource(sourceJavaClass, targetJavaClass, link);
			}
		}
	}

	private void mapJavaTarget(JavaClassSource sourceJavaClass, JavaClassSource targetJavaClass, Link link) {
		targetJavaClass.addImport(List.class);
		targetJavaClass.addImport(ArrayList.class);
		targetJavaClass.addImport(sourceJavaClass);
		targetJavaClass.addImport("javax.persistence.ManyToMany");
		// @formatter:off
		FieldSource<JavaClassSource> field = targetJavaClass.addField()
			.setPrivate()
			.setType(String.format("List<%s>", sourceJavaClass.getName()))
			.setName(Inflector.getInstance().pluralize(Strman.lowerFirst(sourceJavaClass.getName())))
			.setLiteralInitializer("new ArrayList<>()");
		// @formatter:on
		AnnotationSource<JavaClassSource> ann = field.addAnnotation("ManyToMany");
		if (link.getLinkArrow().equals(LinkArrow.BACKWARD)) ann.setStringValue("mappedBy", Inflector.getInstance().pluralize(Strman.lowerFirst(sourceJavaClass.getName())));

		this.addBatchSizeAnnotation(targetJavaClass, field);
		this.addLazyCollectionAnnotation(targetJavaClass, field);
	}

	private void mapJavaSource(JavaClassSource sourceJavaClass, JavaClassSource targetJavaClass, Link link) {
		sourceJavaClass.addImport(List.class);
		sourceJavaClass.addImport(ArrayList.class);
		sourceJavaClass.addImport(targetJavaClass);
		sourceJavaClass.addImport("javax.persistence.ManyToMany");
		// @formatter:off
		FieldSource<JavaClassSource> field = sourceJavaClass.addField()
			.setPrivate()
			.setType(String.format("List<%s>", targetJavaClass.getName()))
			.setName(Inflector.getInstance().pluralize(Strman.lowerFirst(targetJavaClass.getName())))
			.setLiteralInitializer("new ArrayList<>()");
		// @formatter:on
		AnnotationSource<JavaClassSource> ann = field.addAnnotation("ManyToMany");
		if (link.getLinkArrow().equals(LinkArrow.DIRECT_NORMAL) || link.getLinkArrow().equals(LinkArrow.NONE))
			ann.setStringValue("mappedBy", Inflector.getInstance().pluralize(Strman.lowerFirst(targetJavaClass.getName())));

		this.addBatchSizeAnnotation(sourceJavaClass, field);
		this.addLazyCollectionAnnotation(sourceJavaClass, field);
	}

	/*
	 * Adicionar a annotation @LazyCollection
	 */
	private void addLazyCollectionAnnotation(JavaClassSource javaClass, FieldSource<JavaClassSource> field) {
		javaClass.addImport("org.hibernate.annotations.LazyCollection");
		javaClass.addImport("org.hibernate.annotations.LazyCollectionOption");
		field.addAnnotation("LazyCollection").setLiteralValue("LazyCollectionOption.EXTRA");
	}

	/*
	 * Adicionar a annotation @BatchSize
	 */
	private void addBatchSizeAnnotation(JavaClassSource javaClass, FieldSource<JavaClassSource> field) {
		javaClass.addImport("org.hibernate.annotations.BatchSize");
		field.addAnnotation("BatchSize").setLiteralValue("size", "10");
	}

}
