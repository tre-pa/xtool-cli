package br.xtool.core.representation.impl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.xtool.core.representation.PlantFieldRepresentation;
import br.xtool.core.representation.PlantFieldPropertyRepresentation;

public class EPlantFieldPropertyImpl implements PlantFieldPropertyRepresentation {

	private PlantFieldRepresentation field;

	private String property;

	public EPlantFieldPropertyImpl(PlantFieldRepresentation field, String property) {
		super();
		this.field = field;
		this.property = property;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlFieldProperty#getFieldProperty()
	 */
	@Override
	public FieldPropertyType getFieldProperty() {
		String invalidProperty = "Proriedade '%s' inválida no atributo %s. Os tipos válidos são: %s";
		// @formatter:off
		String properties = Stream.of(FieldPropertyType.values())
				.map(FieldPropertyType::getProperty)
				.collect(Collectors.joining(","));
		return Stream.of(FieldPropertyType.values())
				.filter(fieldPropertyType -> fieldPropertyType.getProperty().equals(this.property))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(String.format(invalidProperty, this.property, this.field.getName(), properties)));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlFieldProperty#getField()
	 */
	@Override
	public PlantFieldRepresentation getField() {
		return this.field;
	}

	@Override
	public boolean isNotNull() {
		return getFieldProperty().equals(FieldPropertyType.NOTNULL);
	}

	@Override
	public boolean isUnique() {
		return getFieldProperty().equals(FieldPropertyType.UNIQUE);
	}

	@Override
	public boolean isTransient() {
		return getFieldProperty().equals(FieldPropertyType.TRANSIENT);
	}

}
