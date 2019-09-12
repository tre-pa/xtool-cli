package br.xtool.core.pdiagram.visitor;

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

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.springframework.stereotype.Component;

import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation.FieldPropertyType;
import br.xtool.core.pdiagram.FieldVisitor;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.springboot.JpaEntityAttributeRepresentation;
import lombok.val;

@Component
public class EntityAttributeVisitor implements FieldVisitor {

	@Override
	public void visit(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		this.addSizeAnnotation(attr, plantField);
		this.addMinAnnotation(attr, plantField);
		this.addMaxAnnotation(attr, plantField);
		this.addIdAnnotation(attr, plantField);
		this.addColumnAnnotation(attr, plantField);
		this.addEnumeratedAnnotation(attr);
		this.addLabelTag(attr, plantField);
		this.addMaskTag(attr, plantField);
	}

	private void addMaskTag(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		plantField.getTaggedValue("mask").ifPresent(tagValue -> attr.getRoasterField().getJavaDoc().addTagValue("@mask", tagValue));
	}

	private void addLabelTag(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		// @formatter:off
		plantField.getTaggedValue("label")
			.map(tagValue -> attr.addTagValue("@label", tagValue))
			.orElseGet(() -> attr.addTagValue("@label", attr.getName()));
		// @formatter:on
//		plantField.getTaggedValue("label").ifPresent(tagValue -> attr.getRoasterField().getJavaDoc().addTagValue("@label", tagValue));
	}

	private void addColumnAnnotation(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		val annColumn = attr.addAnnotation(Column.class);
		plantField.getTaggedValue("column.name").ifPresent(tagValue -> annColumn.getRoasterAnnotation().setStringValue("name", tagValue));
		plantField.getProperty(FieldPropertyType.NOTNULL).ifPresent(property -> annColumn.getRoasterAnnotation().setLiteralValue("nullable", "false"));
		plantField.getProperty(FieldPropertyType.UNIQUE).ifPresent(property -> annColumn.getRoasterAnnotation().setLiteralValue("unique", "true"));
		if (attr.isStringField()) annColumn.getRoasterAnnotation().setLiteralValue("length", String.valueOf(plantField.getUpperBoundMultiplicity().orElse(255)));

	}

	private void addIdAnnotation(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
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

	private void addEnumeratedAnnotation(JpaEntityAttributeRepresentation attr) {
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

	private void addMaxAnnotation(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		if (attr.isNumberField()) {
			// @formatter:off
			plantField.getUpperBoundMultiplicity().ifPresent(maxValue -> 
				attr.addAnnotation(Max.class)
					.getRoasterAnnotation()
					.setLiteralValue(String.valueOf(maxValue)));
			// @formatter:on
		}
	}

	private void addMinAnnotation(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		if (attr.isNumberField()) {
			// @formatter:off
			plantField.getLowerBoundMultiplicity().ifPresent(minValue -> 
				attr.addAnnotation(Min.class)
					.getRoasterAnnotation()
					.setLiteralValue(String.valueOf(minValue)));
			// @formatter:on

		}
	}

	private void addSizeAnnotation(JpaEntityAttributeRepresentation attr, PlantClassFieldRepresentation plantField) {
		if (attr.isStringField()) {
			val ann = attr.addAnnotation(Size.class);
			ann.getRoasterAnnotation().setLiteralValue("max", String.valueOf(plantField.getUpperBoundMultiplicity().orElse(255)));
			plantField.getLowerBoundMultiplicity().ifPresent(v -> ann.getRoasterAnnotation().setLiteralValue("min", String.valueOf(v)));
		}
	}


}
