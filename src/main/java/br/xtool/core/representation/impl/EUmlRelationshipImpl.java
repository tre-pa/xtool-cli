package br.xtool.core.representation.impl;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlMultiplicity;
import br.xtool.core.representation.EUmlRelationship;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkArrow;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;

public class EUmlRelationshipImpl implements EUmlRelationship {

	private Link link;

	private EUmlClass sourceClass;

	private EUmlClass targetClass;

	public EUmlRelationshipImpl(EUmlClass sourceClass, EUmlClass targetClass, Link link) {
		super();
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
		this.link = link;

	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getNavigability()
	 */
	@Override
	public EUmlNavigability getNavigability() {
		return new EUmlNavigabilityImpl(this.link);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getSourceClass()
	 */
	@Override
	public EUmlClass getSourceClass() {
		return this.sourceClass;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#isSourceClassOwner()
	 */
	@Override
	@Deprecated
	public boolean isSourceClassOwner() {
		return this.link.getLinkArrow().equals(LinkArrow.DIRECT_NORMAL) || this.link.getLinkArrow().equals(LinkArrow.NONE);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetClass()
	 */
	@Override
	public EUmlClass getTargetClass() {
		return this.targetClass;
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
		return !isComposition();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#isComposition()
	 */
	@Override
	public boolean isComposition() {
		return this.link.getType().getDecor1().equals(LinkDecor.COMPOSITION) ^ this.link.getType().getDecor2().equals(LinkDecor.COMPOSITION);
	}

	private String getSourceQualifier() {
		return StringUtils.trim(this.link.getQualifier1());
	}

	private String getTargetQualifier() {
		return StringUtils.trim(this.link.getQualifier2());
	}

}
