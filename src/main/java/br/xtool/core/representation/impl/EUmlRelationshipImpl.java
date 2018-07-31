package br.xtool.core.representation.impl;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlMultiplicity;
import br.xtool.core.representation.EUmlRelationship;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkArrow;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;

public class EUmlRelationshipImpl implements EUmlRelationship {

	private Link link;

	private Set<EUmlClass> classes;

	public EUmlRelationshipImpl(Set<EUmlClass> classes, Link link) {
		super();
		this.link = link;
		this.classes = classes;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#isBidirectional()
	 */
	@Override
	public boolean isBidirectional() {
		return this.link.getType().getDecor1().equals(LinkDecor.ARROW) && this.link.getType().getDecor2().equals(LinkDecor.ARROW);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#isUnidirectional()
	 */
	@Override
	public boolean isUnidirectional() {
		return this.link.getType().getDecor1().equals(LinkDecor.ARROW) ^ this.link.getType().getDecor2().equals(LinkDecor.ARROW);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getSourceClass()
	 */
	@Override
	public EUmlClass getSourceClass() {
		String error = "Classe '%s' não definida no pacote. Insira a definção da classe e os atributos correspondentes no pacote.";
		// @formatter:off
		return this.classes.stream()
				.filter(umlClass -> umlClass.getName().equals(this.link.getEntity1().getDisplay().asStringWithHiddenNewLine()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format(error, this.link.getEntity1().getDisplay().asStringWithHiddenNewLine())));
		// @formatter:on
	}

	@Override
	public boolean isSourceClassOwner() {
		return this.link.getLinkArrow().equals(LinkArrow.DIRECT_NORMAL) || this.link.getLinkArrow().equals(LinkArrow.NONE);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetClass()
	 */
	@Override
	public EUmlClass getTargetClass() {
		String error = "Classe '%s' não definida no pacote. Insira a definção da classe e os atributos correspondentes no pacote.";
		// @formatter:off
		return this.classes.stream()
				.filter(umlClass -> umlClass.getName().equals(this.link.getEntity2().getDisplay().asStringWithHiddenNewLine()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format(error, this.link.getEntity1().getDisplay().asStringWithHiddenNewLine())));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getSourceMutiplicity()
	 */
	@Override
	public EUmlMultiplicity getSourceMultiplicity() {
		return new EUmlMultiplicityImpl(this.getSourceClass(), this.getTargetClass(), getSourceQualifier());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetMultiplicity()
	 */
	@Override
	public EUmlMultiplicity getTargetMultiplicity() {
		return new EUmlMultiplicityImpl(this.getSourceClass(), this.getTargetClass(), getTargetQualifier());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#isAssociation()
	 */
	@Override
	public boolean isAssociation() {
		return !isComposition() && (this.link.getType().getDecor1().equals(LinkDecor.ARROW) || this.link.getType().getDecor2().equals(LinkDecor.ARROW));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#isComposition()
	 */
	@Override
	public boolean isComposition() {
		return this.link.getType().getDecor1().equals(LinkDecor.COMPOSITION) || this.link.getType().getDecor2().equals(LinkDecor.COMPOSITION);
	}

	private String getSourceQualifier() {
		return StringUtils.trim(this.link.getQualifier1());
	}

	private String getTargetQualifier() {
		return StringUtils.trim(this.link.getQualifier2());
	}

}
