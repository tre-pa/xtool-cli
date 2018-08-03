package br.xtool.core.visitor;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EUmlFieldProperty;

public interface PropertyVisitor extends Predicate<EUmlFieldProperty>, BiConsumer<EJavaClass, EUmlFieldProperty> {

}
