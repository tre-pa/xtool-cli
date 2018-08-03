package br.xtool.core.visitor;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import br.xtool.core.representation.EJavaField;
import br.xtool.core.representation.EUmlField;

public interface FieldVisitor extends Predicate<EUmlField>, BiConsumer<EJavaField, EUmlField> {

}
