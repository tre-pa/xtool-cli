package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import br.xtool.core.representation.EPlantClassDiagram;
import br.xtool.core.representation.EPlantEnum;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class EPlantEnumImpl extends EPlantEntityImpl implements EPlantEnum {

	private EPlantClassDiagram umlClassDiagram;

	private ILeaf leaf;

	public EPlantEnumImpl(EPlantClassDiagram umlClassDiagram, ILeaf leaf) {
		super(leaf);
		this.umlClassDiagram = umlClassDiagram;
		this.leaf = leaf;
	}

	@Override
	public EPlantClassDiagram getClassDiagram() {
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
