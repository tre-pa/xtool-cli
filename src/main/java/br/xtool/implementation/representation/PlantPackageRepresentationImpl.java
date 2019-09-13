package br.xtool.implementation.representation;

import br.xtool.representation.plantuml.PlantPackageRepresentation;
import net.sourceforge.plantuml.cucadiagram.IGroup;

public class PlantPackageRepresentationImpl implements PlantPackageRepresentation {

	private IGroup group;

	public PlantPackageRepresentationImpl(IGroup group) {
		super();
		this.group = group;
	}

	@Override
	public String getName() {
		return this.group.getCode().getFullName();
	}

}
