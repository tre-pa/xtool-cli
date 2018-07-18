package br.xtool.core.representation;

import java.util.Set;
import java.util.SortedSet;

public interface EEntity extends EClass {

	SortedSet<EAttribute> getAttributes();

	Set<ERelationship> getRelationship();
}
