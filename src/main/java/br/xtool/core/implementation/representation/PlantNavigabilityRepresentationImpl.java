package br.xtool.core.implementation.representation;

import br.xtool.core.representation.plantuml.PlantNavigabilityRepresentation;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;

public class PlantNavigabilityRepresentationImpl implements PlantNavigabilityRepresentation {

	private Link link;

	public PlantNavigabilityRepresentationImpl(Link link) {
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

	private LinkDecor getSourceLinkDecor() {
		return this.link.getType().getDecor1();
	}

	private LinkDecor getTargetLinkDecor() {
		return this.link.getType().getDecor2();
	}

}
