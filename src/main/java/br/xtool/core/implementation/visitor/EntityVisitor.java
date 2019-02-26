package br.xtool.core.implementation.visitor;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation.StereotypeType;
import br.xtool.core.representation.springboot.EntityRepresentation;
import br.xtool.core.visitor.ClassVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
public class EntityVisitor implements ClassVisitor {

	@Override
	public void visit(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		addEntityAnnotation(entity);
		addDynamicAnnotation(entity, plantClass);
		addTableAnnotation(entity, plantClass);
		addJsonIncludeAnnotation(entity);
		addAccessorsAnnotation(entity);
		AddNoArgsConstructorAnnotation(entity);
		addToStringAnnotation(entity, plantClass);
		addEqualsAndHashCodeAnnotation(entity, plantClass);
		addPluralClassName(entity, plantClass);
	}

	private void addPluralClassName(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		plantClass.getTaggedValue("plural").ifPresent(tagValue -> entity.getRoasterJavaClass().getJavaDoc().addTagValue("@plural", tagValue));
	}

	private void addEqualsAndHashCodeAnnotation(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		// @formatter:off
		plantClass.getTaggedValueAsArray("EqualsAndHashCode.of").ifPresent(tagValues -> entity
				.addAnnotation(EqualsAndHashCode.class)
				.getRoasterAnnotation()
				.setStringArrayValue("of", tagValues));
		// @formatter:on
	}

	private void addToStringAnnotation(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		// @formatter:off
		plantClass.getTaggedValueAsArray("ToString.of").ifPresent(tagValues -> entity
				.addAnnotation(ToString.class)
				.getRoasterAnnotation()
				.setStringArrayValue("of", tagValues));
		// @formatter:on
	}

	private void AddNoArgsConstructorAnnotation(EntityRepresentation entity) {
		entity.addAnnotation(NoArgsConstructor.class);
	}

	private void addAccessorsAnnotation(EntityRepresentation entity) {
		entity.addAnnotation(Getter.class);
		entity.addAnnotation(Setter.class);
	}

	private void addJsonIncludeAnnotation(EntityRepresentation entity) {
		entity.addAnnotation(JsonInclude.class).getRoasterAnnotation().setEnumArrayValue(JsonInclude.Include.NON_EMPTY);
	}

	private void addTableAnnotation(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		// @formatter:off
		plantClass.getTaggedValue("Table.name").ifPresent(tagValue ->
			entity.addAnnotation(Table.class)
				.getRoasterAnnotation()
				.setStringValue("name", tagValue));
		plantClass.getTaggedValue("Table.schema").ifPresent(tagValue -> 
			entity.addAnnotation(Table.class)
				.getRoasterAnnotation()
				.setStringValue("schema", tagValue));
		// @formatter:on
	}

	private void addDynamicAnnotation(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		if (plantClass.getStereotypes().stream().noneMatch(st -> st.getStereotypeType().equals(StereotypeType.READ_ONLY) || st.getStereotypeType().equals(StereotypeType.VIEW))) {
			entity.addAnnotation(DynamicInsert.class);
			entity.addAnnotation(DynamicUpdate.class);
		}
	}

	private void addEntityAnnotation(EntityRepresentation entity) {
		entity.addAnnotation(Entity.class);
	}

}
