package br.xtool.core.visitor;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EUmlClass;

public interface ClassVisitor extends Predicate<EUmlClass>, BiConsumer<JavaClassSource, EUmlClass> {

}
