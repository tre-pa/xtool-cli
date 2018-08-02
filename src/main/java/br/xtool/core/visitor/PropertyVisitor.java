package br.xtool.core.visitor;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EUmlFieldProperty;

public interface PropertyVisitor extends Predicate<EUmlFieldProperty>, BiConsumer<JavaClassSource, EUmlFieldProperty> {

}
