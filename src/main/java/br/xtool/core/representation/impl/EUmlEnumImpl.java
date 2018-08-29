package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import br.xtool.core.representation.EUmlClassDiagram;
import br.xtool.core.representation.EUmlEnum;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class EUmlEnumImpl extends EUmlEntityImpl implements EUmlEnum {

	private EUmlClassDiagram umlClassDiagram;

	private ILeaf leaf;

	public EUmlEnumImpl(EUmlClassDiagram umlClassDiagram, ILeaf leaf) {
		super(leaf);
		this.umlClassDiagram = umlClassDiagram;
		this.leaf = leaf;
	}

	@Override
	public EUmlClassDiagram getClassDiagram() {
		return this.umlClassDiagram;
	}

	@Override
	public Collection<String> getValues() {
		// @formatter:off
		return this.leaf.getBodier().getFieldsToDisplay().stream()
				.filter(member -> !StringUtils.isEmpty(member.getDisplay(false)))
				.map(member -> member.getDisplay(false))
				.collect(Collectors.toList());
		// @formatter:on
	}

}
