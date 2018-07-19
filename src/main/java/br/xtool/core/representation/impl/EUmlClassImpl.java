package br.xtool.core.representation.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlPackage;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class EUmlClassImpl implements EUmlClass {

	private ILeaf leaf;

	public EUmlClassImpl(ILeaf leaf) {
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getPackage()
	 */
	@Override
	public EUmlPackage getPackage() {
		return new EUmlPackageImpl(this.leaf.getParentContainer());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getFields()
	 */
	@Override
	public Set<EUmlField> getFields() {
		// @formatter:off
		return this.leaf.getBodier().getFieldsToDisplay().stream()
				.filter(member -> StringUtils.isNotEmpty(member.getDisplay(false)))
				.map(EUmlFieldImpl::new)
				.collect(Collectors.toSet());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getStereotypes()
	 */
	@Override
	public Set<String> getStereotypes() {
		return new HashSet<>(this.leaf.getStereotype().getLabels(false));
	}

}
