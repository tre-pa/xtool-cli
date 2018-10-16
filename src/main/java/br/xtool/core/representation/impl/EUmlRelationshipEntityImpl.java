package br.xtool.core.representation.impl;

import java.util.Map;
import java.util.Set;

import br.xtool.core.representation.EPlantClass;
import br.xtool.core.representation.EPlantMultiplicity;
import br.xtool.core.representation.EPlantRelationshipEntity;
import net.sourceforge.plantuml.cucadiagram.Link;

public class EUmlRelationshipEntityImpl implements EPlantRelationshipEntity {

	private Set<EPlantClass> classes;

	private Map<String, String> taggedValues;

	private Link link;

	private boolean source;

	public EUmlRelationshipEntityImpl(Set<EPlantClass> classes, Map<String, String> taggedValues, Link link, boolean source) {
		super();
		this.classes = classes;
		this.taggedValues = taggedValues;
		this.link = link;
		this.source = source;
	}

	@Override
	public EPlantMultiplicity getMultiplicity() {
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
