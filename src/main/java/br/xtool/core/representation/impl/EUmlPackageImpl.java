package br.xtool.core.representation.impl;

import br.xtool.core.representation.EUmlPackage;
import net.sourceforge.plantuml.cucadiagram.IGroup;

public class EUmlPackageImpl implements EUmlPackage {

	private IGroup group;

	public EUmlPackageImpl(IGroup group) {
		super();
		this.group = group;
	}

	@Override
	public String getName() {
		return this.group.getCode().getFullName();
	}

}
