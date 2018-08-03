package br.xtool.core.visitor;

import java.util.function.BiConsumer;

import br.xtool.core.representation.EJavaClass;
import br.xtool.core.representation.EUmlClass;

public interface ClassVisitor extends BiConsumer<EJavaClass, EUmlClass> {

}
