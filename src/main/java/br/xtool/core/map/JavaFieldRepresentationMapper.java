package br.xtool.core.map;

import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.EntityAttributeRepresentationImpl;
import br.xtool.core.implementation.representation.EntityRepresentationImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ENotNullFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ETransientFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EUniqueFieldImpl;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation.FieldPropertyType;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.representation.springboot.SpringBootProjectRepresentation;

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
	private Set<Visitor> visitors;

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
		this.visitors.forEach(visitor -> visitor.visit(new EntityAttributeRepresentationImpl(project, entity, javaField.getRoasterField()), plantField));
		this.visitors.forEach(visitor -> plantField.getProperties().forEach(plantFieldProperty -> {
			visitor.visit(javaField, plantFieldProperty);
			if (plantFieldProperty.getFieldProperty().equals(FieldPropertyType.NOTNULL)) visitor.visit(new ENotNullFieldImpl(javaField), plantFieldProperty);
			if (plantFieldProperty.getFieldProperty().equals(FieldPropertyType.UNIQUE)) visitor.visit(new EUniqueFieldImpl(javaField), plantFieldProperty);
			if (plantFieldProperty.getFieldProperty().equals(FieldPropertyType.TRANSIENT)) visitor.visit(new ETransientFieldImpl(javaField), plantFieldProperty);
		}));
	}

}
