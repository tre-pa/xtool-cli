package br.xtool.core.representation.impl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;

public class EUmlFieldPropertyImpl implements EUmlFieldProperty {

	private EUmlField field;

	private String property;

	public EUmlFieldPropertyImpl(EUmlField field, String property) {
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
	public EUmlField getField() {
		return this.field;
	}

}
