package br.xtool.core.pdiagram.visitor;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jboss.forge.roaster.model.JavaDocTag;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.xtool.core.Clog;
import br.xtool.core.helper.InflectorHelper;
import br.xtool.core.pdiagram.ClassVisitor;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation.StereotypeType;
import br.xtool.core.representation.springboot.EntityRepresentation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import strman.Strman;

@Component
public class EntityVisitor implements ClassVisitor {

	@Override
	public void visit(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		this.addEntityAnnotation(entity);
		this.addDynamicAnnotation(entity, plantClass);
		this.addTableAnnotation(entity, plantClass);
		this.addJsonIncludeAnnotation(entity);
		this.addAccessorsAnnotation(entity);
		this.AddNoArgsConstructorAnnotation(entity);
		this.addToStringAnnotation(entity, plantClass);
		this.addEqualsAndHashCodeAnnotation(entity, plantClass);
		this.addApiPath(entity, plantClass);
	}

	private void addApiPath(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		// @formatter:off
		plantClass.getTaggedValue("api-path")
			.map(tagValue -> entity.addTagValue("@api-path", tagValue))
			.orElseGet(() -> entity.addTagValue("@api-path", InflectorHelper.getInstance().pluralize(Strman.toKebabCase(entity.getName()))));
		// @formatter:on

		// @formatter:off
		Clog.printv(Clog.green(" [DOCKET] "), "@api-path: ", entity.getRoasterJavaClass().getJavaDoc().getTags().stream()
				.filter(javaDocTag -> javaDocTag.getName().equals("@api-path"))
				.map(JavaDocTag::getValue)
				.findFirst()
				.orElse(""), "");
		// @formatter:on
	}

	private void addEqualsAndHashCodeAnnotation(EntityRepresentation entity, PlantClassRepresentation plantClass) {

		if (plantClass.getTaggedValueAsArray("equalsAndHashCode.of").isPresent()) {
			String[] tagValues = plantClass.getTaggedValueAsArray("equalsAndHashCode.of").get();
			// @formatter:off
			entity
				.addAnnotation(EqualsAndHashCode.class)
				.getRoasterAnnotation()
				.setStringArrayValue("of", tagValues);
			// @formatter:on
			Clog.printv(Clog.green(" [ANNOTATION] "), "@EqualAndHashCode: ", "[", StringUtils.join(tagValues, ", "), "]");
		}
	}

	private void addToStringAnnotation(EntityRepresentation entity, PlantClassRepresentation plantClass) {
		// @formatter:off
		plantClass.getTaggedValueAsArray("toString.of").ifPresent(tagValues -> entity
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
		plantClass.getTaggedValue("table.name")
			.map(tagValue ->
				entity.addAnnotation(Table.class)
					.getRoasterAnnotation()
					.setStringValue("name", tagValue))
			.orElse(entity.addAnnotation(Table.class)
					.getRoasterAnnotation()
					.setStringValue("name", Strman.toSnakeCase(entity.getName()).toUpperCase()));
		plantClass.getTaggedValue("table.schema").ifPresent(tagValue -> 
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
