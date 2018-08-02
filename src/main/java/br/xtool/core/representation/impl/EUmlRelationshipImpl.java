package br.xtool.core.representation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlMultiplicity;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.util.Inflector;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkArrow;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;

public class EUmlRelationshipImpl implements EUmlRelationship {

	private Link link;

	private EUmlClass sourceClass;

	private String sourceQualifier;

	private EUmlClass targetClass;

	private String targetQualifier;

	public EUmlRelationshipImpl(EUmlClass sourceClass, EUmlClass targetClass, Link link, String sourceQualifer, String targetQualifier) {
		super();
		this.sourceClass = sourceClass;
		this.sourceQualifier = sourceQualifer;
		this.targetClass = targetClass;
		this.targetQualifier = targetQualifier;
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
		//link.getEntity1().getDisplay().asStringWithHiddenNewLine()
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
		return new EUmlMultiplicityImpl(this.getSourceClass(), this.getTargetClass(), this.sourceQualifier);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlRelationship#getTargetMultiplicity()
	 */
	@Override
	public EUmlMultiplicity getTargetMultiplicity() {
		return new EUmlMultiplicityImpl(this.getSourceClass(), this.getTargetClass(), this.targetQualifier);
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

	@Override
	public FieldSource<JavaClassSource> convertToFieldSource(JavaClassSource javaClassSource) {
		FieldSource<JavaClassSource> fieldSource = javaClassSource.addField();
		if (Stream.of(EUmlMultiplicity.MultiplicityType.ZERO_TO_MANY, EUmlMultiplicity.MultiplicityType.ONE_TO_MANY, EUmlMultiplicity.MultiplicityType.MANY)
				.anyMatch(multiplicity -> this.getSourceMultiplicity().getMutiplicityType() == multiplicity)) {
			// @formatter:off
			javaClassSource.addImport(List.class);
			javaClassSource.addImport(ArrayList.class);
			return fieldSource
					.setPrivate()
					.setName(Inflector.getInstance().pluralize(StringUtils.uncapitalize(this.getTargetClass().getName())))
					.setType(String.format("List<%s>", this.getTargetClass().getName()))
					.setLiteralInitializer("new ArrayList<>()");
			// @formatter:on
		}
		javaClassSource.addImport(this.getTargetClass().getQualifiedName());
		// @formatter:off
		return fieldSource
				.setPrivate()
				.setName(StringUtils.uncapitalize(this.getTargetClass().getName()))
				.setType(this.getTargetClass().getName());
		// @formatter:on
	}

}
