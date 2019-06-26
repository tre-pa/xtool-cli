package br.xtool.core.pdiagram.map;

import java.util.Set;
import java.util.function.Function;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.EntityRepresentationImpl;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl;
import br.xtool.core.pdiagram.ClassVisitor;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Transforma uma classe UML (PlantUML) em um EJavaClass.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaClassRepresentationMapper implements Function<PlantClassRepresentation, JavaClassRepresentation> {

	@Autowired
	private Workspace workspace;

	@Autowired
	private Set<ClassVisitor> classVisitors;

	@Override
	public JavaClassRepresentation apply(PlantClassRepresentation plnatClass) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		// @formatter:off
		JavaClassRepresentation javaClass = springBootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(plnatClass.getName()))
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.map(javaClassSource -> new JavaClassRepresentationImpl(springBootProject, javaClassSource))
				.findFirst()
				.orElseGet(() -> new JavaClassRepresentationImpl(springBootProject,RoasterHelper.createJavaClassSource(plnatClass.getUmlPackage().getName(),plnatClass.getName())));
		// @formatter:on
		this.classVisitors.forEach(visitor -> visitor.visit(new EntityRepresentationImpl(springBootProject, javaClass.getRoasterJavaClass()), plnatClass));
		return javaClass;
	}

}
