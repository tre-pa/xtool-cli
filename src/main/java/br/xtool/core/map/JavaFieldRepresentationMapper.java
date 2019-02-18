package br.xtool.core.map;

import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.helper.RoasterHelper;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ENotNullFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ETransientFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EUniqueFieldImpl;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation.FieldPropertyType;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;

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
	public JavaFieldRepresentation apply(JavaClassRepresentation javaClass, PlantClassFieldRepresentation umlField) {
		JavaFieldRepresentation javaField = javaClass.addField(umlField.getName());
		RoasterHelper.addImport(javaField.getRoasterField().getOrigin(), umlField.getType().getClassName());
		// @formatter:off
		javaField.getRoasterField()
			.setName(umlField.getName())
			.setPrivate()
			.setType(umlField.getType().getJavaName());
		// @formatter:on
		this.visit(javaField, umlField);
		return javaField;
	}

	/*
	 * Visita os atributos da classe e as respectivas propridades.
	 */
	private void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation plantField) {
		this.visitors.forEach(visitor -> visitor.visit(javaField, plantField));
		this.visitors.forEach(visitor -> plantField.getProperties().forEach(plantFieldProperty -> {
			visitor.visit(javaField, plantFieldProperty);
			if (plantFieldProperty.getFieldProperty().equals(FieldPropertyType.NOTNULL)) visitor.visit(new ENotNullFieldImpl(javaField), plantFieldProperty);
			if (plantFieldProperty.getFieldProperty().equals(FieldPropertyType.UNIQUE)) visitor.visit(new EUniqueFieldImpl(javaField), plantFieldProperty);
			if (plantFieldProperty.getFieldProperty().equals(FieldPropertyType.TRANSIENT)) visitor.visit(new ETransientFieldImpl(javaField), plantFieldProperty);
		}));
	}

}
