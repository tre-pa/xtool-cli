package br.xtool.core.diagram.mapper.association;

import java.util.Map;

import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.AssociationMapper;
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
public class OneToOneBidAssociationMapper implements AssociationMapper {

	@Override
	public void map(Map<String, JavaClassSource> javaClasses, Link link) {
		if (this.getAssociationType(link).getDecor1().equals(LinkDecor.ARROW) && this.getAssociationType(link).getDecor2().equals(LinkDecor.ARROW)) {
			String targetQualifier = this.getTargetQualifier(link);
			String sourceQualifier = this.getSourceQualifier(link);
			if (oneMultiplicityPattern.matcher(targetQualifier).matches() && oneMultiplicityPattern.matcher(sourceQualifier).matches()) {
				JavaClassSource sourceJavaClass = this.getJavaClassSource(javaClasses, link);
				JavaClassSource targetJavaClass = this.getJavaClassTarget(javaClasses, link);
				mapJavaTarget(sourceJavaClass, targetJavaClass, link);
				mapJavaSource(sourceJavaClass, targetJavaClass, link);
			}
		}
	}

	private void mapJavaTarget(JavaClassSource sourceJavaClass, JavaClassSource targetJavaClass, Link link) {
		targetJavaClass.addImport("javax.persistence.OneToOne");
		// @formatter:off
		FieldSource<JavaClassSource> field = targetJavaClass.addField()
			.setPrivate()
			.setType(sourceJavaClass)
			.setName(Strman.lowerFirst(sourceJavaClass.getName()));
		// @formatter:on
		AnnotationSource<JavaClassSource> ann = field.addAnnotation("OneToOne");
		if (link.getLinkArrow().equals(LinkArrow.BACKWARD)) ann.setStringValue("mappedBy", Strman.lowerFirst(sourceJavaClass.getName()));
	}

	private void mapJavaSource(JavaClassSource sourceJavaClass, JavaClassSource targetJavaClass, Link link) {
		sourceJavaClass.addImport("javax.persistence.OneToOne");
		// @formatter:off
		FieldSource<JavaClassSource> field = sourceJavaClass.addField()
			.setPrivate()
			.setType(targetJavaClass)
			.setName(Strman.lowerFirst(targetJavaClass.getName()));
		// @formatter:on
		AnnotationSource<JavaClassSource> ann = field.addAnnotation("OneToOne");
		if (link.getLinkArrow().equals(LinkArrow.DIRECT_NORMAL) || link.getLinkArrow().equals(LinkArrow.NONE)) ann.setStringValue("mappedBy", Strman.lowerFirst(targetJavaClass.getName()));
	}

}
