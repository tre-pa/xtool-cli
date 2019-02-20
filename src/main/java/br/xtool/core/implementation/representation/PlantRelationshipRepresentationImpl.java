package br.xtool.core.implementation.representation;

import org.apache.commons.lang3.StringUtils;

import br.xtool.core.helper.InflectorHelper;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantMultiplicityRepresentation;
import br.xtool.core.representation.plantuml.PlantNavigabilityRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkArrow;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;

public class PlantRelationshipRepresentationImpl implements PlantRelationshipRepresentation {

	private Link link;

	private PlantClassRepresentation sourceClass;

	private String sourceQualifier;

	private PlantClassRepresentation targetClass;

	private String targetQualifier;

	public PlantRelationshipRepresentationImpl(PlantClassRepresentation sourceClass, PlantClassRepresentation targetClass, Link link, String sourceQualifer, String targetQualifier) {
		super();
		this.sourceClass = sourceClass;
		this.sourceQualifier = sourceQualifer;
		this.targetClass = targetClass;
		this.targetQualifier = targetQualifier;
		this.link = link;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getSourceQualifier()
	 */
	@Override
	public String getSourceQualifier() {
		return this.sourceQualifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetQualifier()
	 */
	@Override
	public String getTargetQualifier() {
		return this.targetQualifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getNavigability()
	 */
	@Override
	public PlantNavigabilityRepresentation getNavigability() {
		return new PlantNavigabilityRepresentationImpl(this.link);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getSourceClass()
	 */
	@Override
	public PlantClassRepresentation getSourceClass() {
		return this.sourceClass;
	}

	@Override
	public String getSourceRole() {
		if (this.getSourceMultiplicity().isToMany()) {
			return InflectorHelper.getInstance().pluralize(StringUtils.uncapitalize(this.getTargetClass().getName()));
		}
		return StringUtils.uncapitalize(this.getTargetClass().getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#isSourceClassOwner()
	 */
	@Override
	public boolean isSourceClassOwner() {
		if (this.sourceClass.getName().equals(this.link.getEntity1().getDisplay().asStringWithHiddenNewLine())) {
			return this.link.getLinkArrow().equals(LinkArrow.DIRECT_NORMAL) || this.link.getLinkArrow().equals(LinkArrow.NONE);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetClass()
	 */
	@Override
	public PlantClassRepresentation getTargetClass() {
		return this.targetClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetRole()
	 */
	@Override
	public String getTargetRole() {
		if (this.getTargetMultiplicity().isToMany()) {
			return InflectorHelper.getInstance().pluralize(StringUtils.uncapitalize(this.getSourceClass().getName()));
		}
		return StringUtils.uncapitalize(this.getSourceClass().getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getSourceMutiplicity()
	 */
	@Override
	public PlantMultiplicityRepresentation getSourceMultiplicity() {
		return new PlantMultiplicityRepresentationImpl(this.getSourceClass(), this.getTargetClass(), this.sourceQualifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetMultiplicity()
	 */
	@Override
	public PlantMultiplicityRepresentation getTargetMultiplicity() {
		return new PlantMultiplicityRepresentationImpl(this.getSourceClass(), this.getTargetClass(), this.targetQualifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#isAssociation()
	 */
	@Override
	public boolean isAssociation() {
		return !isComposition();
	}

	@Override
	public Link getLink() {
		return this.link;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlRelationship#isComposition()
	 */
	@Override
	public boolean isComposition() {
		return this.link.getType().getDecor1().equals(LinkDecor.COMPOSITION) ^ this.link.getType().getDecor2().equals(LinkDecor.COMPOSITION);
	}

	@Override
	public boolean isOneToOne() {
		return this.getSourceMultiplicity().isToOne() && this.getTargetMultiplicity().isToOne();
	}

	@Override
	public boolean isOneToMany() {
		return this.getSourceMultiplicity().isToMany() && this.getTargetMultiplicity().isToOne();
	}

	@Override
	public boolean isManyToOne() {
		return this.getSourceMultiplicity().isToOne() && this.getTargetMultiplicity().isToMany();
	}

	@Override
	public boolean isManyToMany() {
		return this.getSourceMultiplicity().isToMany() && this.getTargetMultiplicity().isToMany();
	}

}
