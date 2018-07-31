package br.xtool.core.visitor;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;

public interface Visitor {

	/**
	 * Visita a classe UML.
	 * 
	 * @param umlClass
	 */
	void visitClass(EUmlClass umlClass);

	/**
	 * Visita o atributo Id.
	 * 
	 * @param umlField
	 */
	void visitIdField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo Long.
	 * 
	 * @param umlField
	 */
	void visitLongField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo Boolean.
	 * 
	 * @param umlField
	 */
	void visitBooleanField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo String.
	 * 
	 * @param umlField
	 */
	void visitStringField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo Integer.
	 * 
	 * @param umlField
	 */
	void visitIntegerField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo BigDecimal.
	 * 
	 * @param umlField
	 */
	void visitBigDecimalField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo Array de bytes.
	 * 
	 * @param umlField
	 */
	void visitByteArrayField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo LocalDate.
	 * 
	 * @param umlField
	 */
	void visitLocalDateField(EUmlField umlField);

	/**
	 * Visita o atributo do tipo LocalDateTime.
	 * 
	 * @param umlField
	 */
	void visitLocalDateTimeField(EUmlField umlField);

	/**
	 * Visita atributo com propriedade unique.
	 * 
	 * @param umlFieldProperty
	 */
	void visitUniqueProperty(EUmlFieldProperty umlFieldProperty);

	/**
	 * Visita atributo com propriedade notNull.
	 * 
	 * @param umlFieldProperty
	 */
	void visitNotNullProperty(EUmlFieldProperty umlFieldProperty);

	/**
	 * Visita atributo com propriedade Transient.
	 * 
	 * @param umlFieldProperty
	 */
	void visitTransientProperty(EUmlFieldProperty umlFieldProperty);

	void visit(EUmlRelationship umlRelationship);
}
