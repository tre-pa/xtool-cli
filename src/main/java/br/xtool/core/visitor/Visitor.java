package br.xtool.core.visitor;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;

public interface Visitor {

	void visit(EUmlClass umlClass);

	void visit(EUmlField umlField);

	void visitIdField(EUmlField umlField);

	void visitLongField(EUmlField umlField);

	void visitBooleanField(EUmlField umlField);

	void visitStringField(EUmlField umlField);

	void visitIntegerField(EUmlField umlField);

	void visitBigDecimalField(EUmlField umlField);

	void visitByteArrayField(EUmlField umlField);

	void visitLocalDateField(EUmlField umlField);

	void visitLocalDateTimeField(EUmlField umlField);

	void visitUniqueProperty(EUmlFieldProperty umlFieldProperty);

	void visitNotNullProperty(EUmlFieldProperty umlFieldProperty);

	void visitTransientProperty(EUmlFieldProperty umlFieldProperty);

	void visit(EUmlRelationship umlRelationship);
}
