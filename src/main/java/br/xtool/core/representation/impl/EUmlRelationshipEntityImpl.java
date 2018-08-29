package br.xtool.core.representation.impl;

import java.util.Map;
import java.util.Set;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlMultiplicity;
import br.xtool.core.representation.EUmlRelationshipEntity;
import net.sourceforge.plantuml.cucadiagram.Link;

public class EUmlRelationshipEntityImpl implements EUmlRelationshipEntity {

	private Set<EUmlClass> classes;

	private Map<String, String> taggedValues;

	private Link link;

	private boolean source;

	public EUmlRelationshipEntityImpl(Set<EUmlClass> classes, Map<String, String> taggedValues, Link link, boolean source) {
		super();
		this.classes = classes;
		this.taggedValues = taggedValues;
		this.link = link;
		this.source = source;
	}

	@Override
	public EUmlMultiplicity getMultiplicity() {
		return null;
	}

	@Override
	public String getRole() {
		return null;
	}

	@Override
	public boolean isOptional() {
		return false;
	}

}
