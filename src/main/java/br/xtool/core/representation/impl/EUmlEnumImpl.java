package br.xtool.core.representation.impl;

import java.util.Set;

import br.xtool.core.representation.EUmlEnum;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class EUmlEnumImpl implements EUmlEnum {

	private ILeaf leaf;

	public EUmlEnumImpl(ILeaf leaf) {
		super();
		this.leaf = leaf;
	}

	@Override
	public Set<String> getValues() {
		throw new UnsupportedOperationException();
	}

}
