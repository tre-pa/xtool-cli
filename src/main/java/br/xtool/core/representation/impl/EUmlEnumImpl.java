package br.xtool.core.representation.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import br.xtool.core.representation.EUmlEnum;
import br.xtool.core.representation.EUmlPackage;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class EUmlEnumImpl implements EUmlEnum {

	private ILeaf leaf;

	public EUmlEnumImpl(ILeaf leaf) {
		super();
		this.leaf = leaf;
	}

	@Override
	public String getName() {
		return this.leaf.getDisplay().asStringWithHiddenNewLine();
	}

	@Override
	public EUmlPackage getPackage() {
		return new EUmlPackageImpl(this.leaf.getParentContainer());
	}

	@Override
	public Set<String> getValues() {
		// @formatter:off
		return this.leaf.getBodier().getFieldsToDisplay().stream()
				.filter(member -> !StringUtils.isEmpty(member.getDisplay(false)))
				.map(member -> member.getDisplay(false))
				.collect(Collectors.toSet());
		// @formatter:on
	}

}
