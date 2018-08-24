package br.xtool.core.representation.impl;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EUmlEntity;
import br.xtool.core.representation.EUmlPackage;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class EUmlEntityImpl implements EUmlEntity {

	private ILeaf leaf;

	public EUmlEntityImpl(ILeaf leaf) {
		super();
		this.leaf = leaf;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getName()
	 */
	@Override
	public String getName() {
		return this.leaf.getDisplay().asStringWithHiddenNewLine();
	}

	@Override
	public String getInstanceName() {
		return StringUtils.uncapitalize(this.getName());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getQualifiedName()
	 */
	@Override
	public String getQualifiedName() {
		return this.getUmlPackage().getName().concat(".").concat(getName());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getPackage()
	 */
	@Override
	public EUmlPackage getUmlPackage() {
		return new EUmlPackageImpl(this.leaf.getParentContainer());
	}

}
