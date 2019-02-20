package br.xtool.core.map;

import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.EntityAttributeRepresentationImpl;
import br.xtool.core.implementation.representation.EntityRepresentationImpl;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;
import br.xtool.core.visitor.FieldVisitor;

/**
 * Transforma um atributo UML do diagrama de classe em um EJavaField.
 * 
 * @author jcruz
 *
 */
//@AllArgsConstructor
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
		EntityRepresentation entity = new EntityRepresentationImpl(project, javaField.getJavaClass().getRoasterJavaClass());
		EntityAttributeRepresentation attribute = new EntityAttributeRepresentationImpl(project, entity, javaField.getRoasterField());
		this.fieldVisitors.forEach(visitor -> visitor.visit(attribute, plantField));
//		this.fieldVisitors.forEach(visitor -> plantField.getProperties().forEach(plantFieldProperty -> visitor.visit(attribute, plantFieldProperty)));
	}

}
