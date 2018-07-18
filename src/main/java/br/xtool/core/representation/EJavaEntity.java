package br.xtool.core.representation;

import java.util.Set;
import java.util.SortedSet;

public interface EJavaEntity extends EJavaClass {

	SortedSet<EJavaAttribute> getAttributes();

	Set<EJavaRelationship> getRelationship();
}
