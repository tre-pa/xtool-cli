package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.plantuml.PlantEnumRepresentation;
import br.xtool.core.representation.plantuml.PlantPackageRepresentation;
import net.sourceforge.plantuml.cucadiagram.ILeaf;

public class PlantEnumRepresentationImpl implements PlantEnumRepresentation {

	private PlantClassDiagramRepresentation umlClassDiagram;

	private ILeaf leaf;

	public PlantEnumRepresentationImpl(PlantClassDiagramRepresentation umlClassDiagram, ILeaf leaf) {
		this.umlClassDiagram = umlClassDiagram;
		this.leaf = leaf;
	}

	@Override
	public PlantClassDiagramRepresentation getClassDiagram() {
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

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getQualifiedName()
	 */
	@Override
	public String getQualifiedName() {
		return this.getUmlPackage().getName().concat(".").concat(getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getPackage()
	 */
	@Override
	public PlantPackageRepresentation getUmlPackage() {
		return new PlantPackageRepresentationImpl(this.leaf.getParentContainer());
	}

}
