package br.xtool.core.diagram.mapper.association;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.stereotype.Component;

import br.xtool.core.diagram.mapper.AssociationMapper;
import br.xtool.core.util.Inflector;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import strman.Strman;

/**
 * Mapeia as associações OneToMany bidirecionais.
 * 
 * @author jcruz
 *
 */
@Component
public class OneToManyBidAssociationMapper implements AssociationMapper {

	@Override
	public void map(Map<String, JavaClassSource> javaClasses, Link link) {
		if (this.getAssociationType(link).getDecor1().equals(LinkDecor.ARROW) && this.getAssociationType(link).getDecor2().equals(LinkDecor.ARROW)) {
			String targetQualifier = this.getTargetQualifier(link);
			String sourceQualifier = this.getSourceQualifier(link);
			if (manyMultiplicityPattern.matcher(targetQualifier).matches() && oneMultiplicityPattern.matcher(sourceQualifier).matches()) {
				JavaClassSource sourceJavaClass = this.getJavaClassSource(javaClasses, link);
				JavaClassSource targetJavaClass = this.getJavaClassTarget(javaClasses, link);
				mapJavaTarget(sourceJavaClass, targetJavaClass);
				mapJavaSource(sourceJavaClass, targetJavaClass);

			}
		}
	}

	private void mapJavaTarget(JavaClassSource sourceJavaClass, JavaClassSource targetJavaClass) {
		targetJavaClass.addImport("javax.persistence.ManyToOne");
		// @formatter:off
		targetJavaClass.addField()
			.setPrivate()
			.setType(sourceJavaClass)
			.setName(Strman.lowerFirst(sourceJavaClass.getName()))
			.addAnnotation("ManyToOne");
		// @formatter:on
	}

	private void mapJavaSource(JavaClassSource sourceJavaClass, JavaClassSource targetJavaClass) {
		// @formatter:off
		sourceJavaClass.addImport("javax.persistence.OneToMany");
		// @formatter:on

		sourceJavaClass.addImport(List.class);
		sourceJavaClass.addImport(ArrayList.class);
		sourceJavaClass.addImport(targetJavaClass);
		// @formatter:off
		FieldSource<JavaClassSource> field = sourceJavaClass
			.addField()
			.setPrivate()
			.setType(String.format("List<%s>", targetJavaClass.getName()))
			.setName(Inflector.getInstance().pluralize(Strman.lowerFirst(targetJavaClass.getName())))
			.setLiteralInitializer("new ArrayList<>()");
		field.addAnnotation("OneToMany")
			.setStringValue("mappedBy", Strman.lowerFirst(sourceJavaClass.getName()));
		// @formatter:on
	}

}
