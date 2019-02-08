package br.xtool.core.map;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.xtool.core.Visitor;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EBigDecimalFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EBooleanFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EByteFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EENumFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EIntegerFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ELocalDateFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ELocalDateTimeFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ELongFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ENotNullFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EStringFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.ETransientFieldImpl;
import br.xtool.core.implementation.representation.JavaFieldRepresentationImpl.EUniqueFieldImpl;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation.FieldPropertyType;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.JavaClassRepresentation;
import br.xtool.core.representation.springboot.JavaFieldRepresentation;
import br.xtool.core.util.RoasterUtil;

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
		RoasterUtil.addImport(javaField.getRoasterField().getOrigin(), umlField.getType().getClassName());
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
	private void visit(JavaFieldRepresentation javaField, PlantClassFieldRepresentation umlField) {
		this.visitors.forEach(visitor -> {
			visitor.visit(javaField, umlField);
			if (javaField.getType().isType(String.class))
				visitor.visit(new EStringFieldImpl(javaField), umlField);
			if (javaField.getType().isType(Boolean.class))
				visitor.visit(new EBooleanFieldImpl(javaField), umlField);
			if (javaField.getType().isType(Long.class))
				visitor.visit(new ELongFieldImpl(javaField), umlField);
			if (javaField.getType().isType(Integer.class))
				visitor.visit(new EIntegerFieldImpl(javaField), umlField);
			if (javaField.getType().isType(Byte.class))
				visitor.visit(new EByteFieldImpl(javaField), umlField);
			if (javaField.getType().isType(BigDecimal.class))
				visitor.visit(new EBigDecimalFieldImpl(javaField), umlField);
			if (javaField.getType().isType(LocalDate.class))
				visitor.visit(new ELocalDateFieldImpl(javaField), umlField);
			if (javaField.getType().isType(LocalDateTime.class))
				visitor.visit(new ELocalDateTimeFieldImpl(javaField), umlField);
			if (umlField.isEnum())
				visitor.visit(new EENumFieldImpl(javaField), umlField);
		});
		this.visitors.forEach(visitor -> umlField.getProperties().forEach(property -> {
			visitor.visit(javaField, property);
			if (property.getFieldProperty().equals(FieldPropertyType.NOTNULL))
				visitor.visit(new ENotNullFieldImpl(javaField), property);
			if (property.getFieldProperty().equals(FieldPropertyType.UNIQUE))
				visitor.visit(new EUniqueFieldImpl(javaField), property);
			if (property.getFieldProperty().equals(FieldPropertyType.TRANSIENT))
				visitor.visit(new ETransientFieldImpl(javaField), property);
		}));
	}

}
