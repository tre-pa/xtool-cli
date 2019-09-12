package br.xtool.core.pdiagram.map;

import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.JpaEntityAttributeRepresentationImpl;
import br.xtool.core.implementation.representation.JpaEntityRepresentationImpl;
import br.xtool.core.pdiagram.FieldVisitor;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.JpaEntityAttributeRepresentation;
import br.xtool.core.representation.springboot.JpaEntityRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

/**
 * Transforma um atributo UML (PlantUML) do diagrama de classe em um EJavaField.
 * 
 * @author jcruz
 *
 */
@Component
public class JavaFieldRepresentationMapper implements BiFunction<JavaClassRepresentation, PlantClassFieldRepresentation, JavaFieldRepresentation> {

	@Autowired
	private Set<FieldVisitor> fieldVisitors;

	@Override
	public JavaFieldRepresentation apply(JavaClassRepresentation javaClass, PlantClassFieldRepresentation plantField) {
		JavaFieldRepresentation javaField = javaClass.addField(plantField.getName());
		RoasterHelper.addImport(javaField.getRoasterField().getOrigin(), plantField.getType().getClassName());
		// @formatter:off
		javaField.getRoasterField()
			.setName(plantField.getName())
			.setPrivate()
			.setType(plantField.getType().getJavaName());
		// @formatter:on
		this.visit(javaField, plantField);
		return javaField;
	}

	/*
	 * Visita os atributos da classe e as respectivas propridades.
	 */
	private void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation plantField) {
		SpringBootProjectRepresentation project = javaField.getJavaClass().getProject();
		JpaEntityRepresentation entity = new JpaEntityRepresentationImpl(project, javaField.getJavaClass().getRoasterJavaClass());
		JpaEntityAttributeRepresentation attribute = new JpaEntityAttributeRepresentationImpl(project, entity, javaField.getRoasterField());
		this.fieldVisitors.forEach(visitor -> visitor.visit(attribute, plantField));
//		this.fieldVisitors.forEach(visitor -> plantField.getProperties().forEach(plantFieldProperty -> visitor.visit(attribute, plantFieldProperty)));
	}

}
