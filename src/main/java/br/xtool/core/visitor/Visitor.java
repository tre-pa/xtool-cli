package br.xtool.core.visitor;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlRelationship;

public interface Visitor {

	void visit(EUmlClass umlClass);

	void visit(EUmlField umlField);

	void visit(EUmlFieldProperty umlFieldProperty);

	void visit(EUmlRelationship umlRelationship);
}
