package br.xtool.core.visitor;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EUmlField;

public interface FieldVisitor extends Predicate<EUmlField>, BiConsumer<JavaClassSource, EUmlField> {

}
