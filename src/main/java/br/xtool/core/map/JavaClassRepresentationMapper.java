package br.xtool.core.map;

import java.util.Set;
import java.util.function.Function;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.Workspace;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl.EAuditableJavaClassImpl;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl.ECacheableJavaClassImpl;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl.EIndexedJavaClassImpl;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl.EReadOnlyJavaClassImpl;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl.EVersionableJavaClassImpl;
import br.xtool.core.implementation.representation.JavaClassRepresentationImpl.EViewJavaClassImpl;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation.StereotypeType;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.util.RoasterUtil;

/**
 * Transforma uma classe UML em um EJavaClass.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaClassRepresentationMapper implements Function<PlantClassRepresentation, JavaClassRepresentation> {

	@Autowired
	private Workspace workspace;

	@Autowired
	private Set<Visitor> visitors;

	@Override
	public JavaClassRepresentation apply(PlantClassRepresentation umlClass) {
		SpringBootProjectRepresentation springBootProject = this.workspace.getWorkingProject(SpringBootProjectRepresentation.class);
		// @formatter:off
		JavaClassRepresentation javaClass = springBootProject.getRoasterJavaUnits().stream()
				.filter(javaUnit -> javaUnit.getGoverningType().isClass())
				.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(umlClass.getName()))
				.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
				.map(javaClassSource -> new JavaClassRepresentationImpl(springBootProject, javaClassSource))
				.findFirst()
				.orElseGet(() -> new JavaClassRepresentationImpl(springBootProject,RoasterUtil.createJavaClassSource(umlClass.getUmlPackage().getName(),umlClass.getName())));
		// @formatter:on
		this.visitors.forEach(visitor -> visitor.visit(javaClass, umlClass));
		umlClass.getStereotypes().stream().forEach(stereotype -> this.visit(javaClass, stereotype));
		return javaClass;
	}

	private void visit(JavaClassRepresentation javaClass, PlantStereotypeRepresentation stereotype) {
		this.visitors.forEach(visitor -> {
			if (stereotype.getStereotypeType().equals(StereotypeType.AUDITABLE)) visitor.visit(new EAuditableJavaClassImpl(javaClass), stereotype);
			if (stereotype.getStereotypeType().equals(StereotypeType.CACHEABLE)) visitor.visit(new ECacheableJavaClassImpl(javaClass), stereotype);
			if (stereotype.getStereotypeType().equals(StereotypeType.INDEXED)) visitor.visit(new EIndexedJavaClassImpl(javaClass), stereotype);
			if (stereotype.getStereotypeType().equals(StereotypeType.VIEW)) visitor.visit(new EViewJavaClassImpl(javaClass), stereotype);
			if (stereotype.getStereotypeType().equals(StereotypeType.READ_ONLY)) visitor.visit(new EReadOnlyJavaClassImpl(javaClass), stereotype);
			if (stereotype.getStereotypeType().equals(StereotypeType.VERSIONABLE)) visitor.visit(new EVersionableJavaClassImpl(javaClass), stereotype);
		});
	}

}
