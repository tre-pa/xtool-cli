package br.xtool.core.implementation.visitor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation.FieldPropertyType;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.EntityAttributeRepresentation;
import br.xtool.core.visitor.FieldVisitor;
import lombok.val;

@Component
public class EntityAttributeVisitor implements FieldVisitor {

	@Override
	public void visit(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		this.addSizeAnnotation(attr, plantField);
		this.addMinAnnotation(attr, plantField);
		this.addMaxAnnotation(attr, plantField);
		this.addIdAnnotation(attr, plantField);
		this.addColumnAnnotation(attr, plantField);
		this.addEnumeratedAnnotation(attr);
		this.addLabelTag(attr, plantField);
		this.addMaskTag(attr, plantField);
	}

	private void addMaskTag(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		plantField.getTaggedValue("mask").ifPresent(tagValue -> attr.getRoasterField().getJavaDoc().addTagValue("@mask", tagValue));
	}

	private void addLabelTag(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		// @formatter:off
		plantField.getTaggedValue("label")
			.map(tagValue -> attr.getRoasterField().getJavaDoc().addTagValue("@label", tagValue))
			.orElseGet(() -> attr.getRoasterField().getJavaDoc().addTagValue("@label", attr.getName()));
		// @formatter:on
//		plantField.getTaggedValue("label").ifPresent(tagValue -> attr.getRoasterField().getJavaDoc().addTagValue("@label", tagValue));
	}

	private void addColumnAnnotation(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		val annColumn = attr.addAnnotation(Column.class);
		plantField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.getRoasterAnnotation().setStringValue("name", tagValue));
		plantField.getProperty(FieldPropertyType.NOTNULL).ifPresent(property -> annColumn.getRoasterAnnotation().setLiteralValue("nullable", "false"));
		plantField.getProperty(FieldPropertyType.UNIQUE).ifPresent(property -> annColumn.getRoasterAnnotation().setLiteralValue("unique", "true"));
		if (attr.isStringField()) annColumn.getRoasterAnnotation().setLiteralValue("length", String.valueOf(plantField.getUpperBoundMultiplicity().orElse(255)));

	}

	private void addIdAnnotation(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		if (attr.isLongField()) {
			if (plantField.isId()) {
				attr.addAnnotation(Id.class);
				// @formatter:off
				attr.addAnnotation(Column.class).getRoasterAnnotation()
					.setLiteralValue("updatable", "false")
					.setLiteralValue("nullable", "false");
				attr.addAnnotation(GeneratedValue.class).getRoasterAnnotation()
					.setEnumValue("strategy", GenerationType.SEQUENCE)
					.setStringValue("generator", attr.getEntity().asDatabaseSequenceName());
				attr.addAnnotation(SequenceGenerator.class).getRoasterAnnotation()
					.setLiteralValue("initialValue", "1")
					.setLiteralValue("allocationSize", "1")
					.setStringValue("name", attr.getEntity().asDatabaseSequenceName())
					.setStringValue("sequenceName", attr.getEntity().asDatabaseSequenceName());
				// @formatter:on
			}
		}
	}

	private void addEnumeratedAnnotation(EntityAttributeRepresentation attr) {
		if (attr.isEnumField()) {
			// @formatter:off
			attr.addAnnotation(Enumerated.class)
				.getRoasterAnnotation()
				.setEnumValue(EnumType.STRING)
				.setEnumValue(EnumType.STRING);
			attr.addAnnotation(Column.class)
				.getRoasterAnnotation()
				.setLiteralValue("nullable", "false");
			// @formatter:on
		}
	}

	private void addMaxAnnotation(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		if (attr.isNumberField()) {
			// @formatter:off
			plantField.getUpperBoundMultiplicity().ifPresent(maxValue -> 
				attr.addAnnotation(Max.class)
					.getRoasterAnnotation()
					.setLiteralValue(String.valueOf(maxValue)));
			// @formatter:on
		}
	}

	private void addMinAnnotation(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		if (attr.isNumberField()) {
			// @formatter:off
			plantField.getLowerBoundMultiplicity().ifPresent(minValue -> 
				attr.addAnnotation(Min.class)
					.getRoasterAnnotation()
					.setLiteralValue(String.valueOf(minValue)));
			// @formatter:on

		}
	}

	private void addSizeAnnotation(EntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		if (attr.isStringField()) {
			val ann = attr.addAnnotation(Size.class);
			ann.getRoasterAnnotation().setLiteralValue("max", String.valueOf(plantField.getUpperBoundMultiplicity().orElse(255)));
			plantField.getLowerBoundMultiplicity().ifPresent(v -> ann.getRoasterAnnotation().setLiteralValue("min", String.valueOf(v)));
		}
	}

}
