package br.xtool.core.representation.impl;

import br.xtool.core.representation.EPlantPackage;
import net.sourceforge.plantuml.cucadiagram.IGroup;

public class EPlantPackageImpl implements EPlantPackage {

	private IGroup group;

	public EPlantPackageImpl(IGroup group) {
		super();
		this.group = group;
	}

	@Override
	public String getName() {
		return this.group.getCode().getFullName();
	}

}
