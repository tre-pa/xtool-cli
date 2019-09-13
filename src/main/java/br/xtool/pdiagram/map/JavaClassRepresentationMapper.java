package br.xtool.pdiagram.map;

import java.util.Set;
import java.util.function.BiFunction;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.helper.RoasterHelper;
import br.xtool.implementation.representation.JpaEntityRepresentationImpl;
import br.xtool.implementation.representation.JavaClassRepresentationImpl;
import br.xtool.pdiagram.ClassVisitor;
import br.xtool.representation.plantuml.PlantClassRepresentation;
import br.xtool.representation.springboot.JavaClassRepresentation;
import br.xtool.representation.springboot.SpringBootProjectRepresentation;

/**
 * Transforma uma classe UML (PlantUML) em um EJavaClass.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaClassRepresentationMapper implements BiFunction<SpringBootProjectRepresentation, PlantClassRepresentation, JavaClassRepresentation> {

	@Autowired
	private Set<ClassVisitor> classVisitors;

	@Override
	public JavaClassRepresentation apply(SpringBootProjectRepresentation springBootProject, PlantClassRepresentation plnatClass) {
		// @formatter:off
		JavaClassRepresentation javaClass = springBootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(plnatClass.getName()))
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.map(javaClassSource -> new JavaClassRepresentationImpl(springBootProject, javaClassSource))
				.findFirst()
				.orElseGet(() -> new JavaClassRepresentationImpl(springBootProject,RoasterHelper.createJavaClassSource(plnatClass.getUmlPackage().getName(),plnatClass.getName())));
		// @formatter:on
		this.classVisitors.forEach(visitor -> visitor.visit(new JpaEntityRepresentationImpl(springBootProject, javaClass.getRoasterJavaClass()), plnatClass));
		return javaClass;
	}

}
