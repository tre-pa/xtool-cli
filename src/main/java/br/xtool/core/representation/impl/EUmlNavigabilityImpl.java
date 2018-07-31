package br.xtool.core.representation.impl;

import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;

public class EUmlNavigabilityImpl implements EUmlNavigability {

	private Link link;

	public EUmlNavigabilityImpl(Link link) {
		super();
		this.link = link;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.impl.EUmlNavigability#isBidirectional()
	 */
	@Override
	public boolean isBidirectional() {
		// @formatter:off
		return 
			   // A <--> B
			   (this.getSourceLinkDecor().equals(LinkDecor.ARROW) && this.getTargetLinkDecor().equals(LinkDecor.ARROW)) ||
			   // A -- B
			   (this.getSourceLinkDecor().equals(LinkDecor.NONE) && this.getTargetLinkDecor().equals(LinkDecor.NONE)) ||
			   // A <--* B
			   (this.getSourceLinkDecor().equals(LinkDecor.COMPOSITION) && this.getTargetLinkDecor().equals(LinkDecor.ARROW)) ||
			   // A *--> B
			   (this.getSourceLinkDecor().equals(LinkDecor.ARROW) && this.getTargetLinkDecor().equals(LinkDecor.COMPOSITION));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.impl.EUmlNavigability#isUnidirectional()
	 */
	@Override
	public boolean isUnidirectional() {
		// @formatter:off
		return 
			   // A --> B	
			   (this.getSourceLinkDecor().equals(LinkDecor.ARROW) && this.getTargetLinkDecor().equals(LinkDecor.NONE)) ||
			   // A <-- B
			   (this.getSourceLinkDecor().equals(LinkDecor.NONE) && this.getTargetLinkDecor().equals(LinkDecor.ARROW)) ||
			   // A --* B
			   (this.getSourceLinkDecor().equals(LinkDecor.COMPOSITION) && this.getTargetLinkDecor().equals(LinkDecor.NONE)) ||
			   // A *-- B
			   (this.getSourceLinkDecor().equals(LinkDecor.NONE) && this.getTargetLinkDecor().equals(LinkDecor.COMPOSITION));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.impl.EUmlNavigability#isSourceToTargetDirection()
	 */
	@Override
	public boolean isSourceToTargetDirection() {
		return this.isUnidirectional() && (this.getSourceLinkDecor().equals(LinkDecor.ARROW) || this.getSourceLinkDecor().equals(LinkDecor.COMPOSITION));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.impl.EUmlNavigability#isTargetToSourceDirection()
	 */
	@Override
	public boolean isTargetToSourceDirection() {
		return this.isUnidirectional() && (this.getTargetLinkDecor().equals(LinkDecor.ARROW) || this.getTargetLinkDecor().equals(LinkDecor.COMPOSITION));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.impl.EUmlNavigability#isBothDirection()
	 */
	@Override
	public boolean isBothDirection() {
		return this.isBidirectional();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.impl.EUmlNavigability#isNoneDirection()
	 */
	@Override
	public boolean isNoneDirection() {
		return this.getSourceLinkDecor().equals(LinkDecor.NONE) && this.getTargetLinkDecor().equals(LinkDecor.NONE);
	}

	private LinkDecor getSourceLinkDecor() {
		return this.link.getType().getDecor1();
	}

	private LinkDecor getTargetLinkDecor() {
		return this.link.getType().getDecor2();
	}

}
