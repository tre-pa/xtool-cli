package br.xtool.core.converter;

import java.util.Set;
import java.util.function.Function;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Workspace;
import br.xtool.core.representation.JavaClassRepresentation;
import br.xtool.core.representation.PlantClassRepresentation;
import br.xtool.core.representation.PlantStereotypeRepresentation;
import br.xtool.core.representation.PlantStereotypeRepresentation.StereotypeType;
import br.xtool.core.representation.SpringBootProjectRepresentation;
import br.xtool.core.representation.impl.JavaClassRepresentationImpl;
import br.xtool.core.representation.impl.JavaClassRepresentationImpl.EAuditableJavaClassImpl;
import br.xtool.core.representation.impl.JavaClassRepresentationImpl.ECacheableJavaClassImpl;
import br.xtool.core.representation.impl.JavaClassRepresentationImpl.EIndexedJavaClassImpl;
import br.xtool.core.representation.impl.JavaClassRepresentationImpl.EReadOnlyJavaClassImpl;
import br.xtool.core.representation.impl.JavaClassRepresentationImpl.EVersionableJavaClassImpl;
import br.xtool.core.representation.impl.JavaClassRepresentationImpl.EViewJavaClassImpl;
import br.xtool.core.util.RoasterUtil;
import br.xtool.core.visitor.Visitor;

/**
 * Converte uma classe UML em um EJavaClass.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaClassRepresentationConverter implements Function<PlantClassRepresentation, JavaClassRepresentation> {

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
