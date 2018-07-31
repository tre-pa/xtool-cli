package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlPackage;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.util.RoasterUtil;
import br.xtool.core.visitor.Visitor;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import strman.Strman;

public class EUmlClassImpl implements EUmlClass {

	private ILeaf leaf;

	public EUmlClassImpl(ILeaf leaf) {
		super();
		this.leaf = leaf;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getName()
	 */
	@Override
	public String getName() {
		return this.leaf.getDisplay().asStringWithHiddenNewLine();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getPackage()
	 */
	@Override
	public EUmlPackage getPackage() {
		return new EUmlPackageImpl(this.leaf.getParentContainer());
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getFields()
	 */
	@Override
	public Collection<EUmlField> getFields() {
		// @formatter:off
		return this.leaf.getBodier().getFieldsToDisplay().stream()
				.filter(member -> StringUtils.isNotEmpty(member.getDisplay(false)))
				.map(EUmlFieldImpl::new)
				.collect(Collectors.toList());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getStereotypes()
	 */
	@Override
	public Set<EUmlStereotype> getStereotypes() {
		// @formatter:off
		if(Objects.nonNull(this.leaf.getStereotype())) {
			return this.leaf.getStereotype().getLabels(false).stream()
					.map(value -> Strman.between(value, "<<", ">>"))
					.map(StringUtils::join)
					.map(value -> new EUmlStereotypeImpl(this, value))
					.collect(Collectors.toSet());
		}
		// @formatter:on
		return new HashSet<>();
	}

	@Override
	public JavaClassSource convertToJavaClassSource(EBootProject bootProject) {
		// @formatter:off
		JavaClassSource javaClassSource = bootProject.getRoasterJavaUnits().stream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(this.getName()))
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.findFirst()
			.orElseGet(() -> RoasterUtil.createJavaClassSource(this.getName()));
		// @formatter:on
		javaClassSource.setPackage(this.getPackage().getName());
		this.getFields().stream().forEach(umlField -> this.createOrUpdateFieldSource(javaClassSource, umlField));
		return javaClassSource;
	}

	private void createOrUpdateFieldSource(JavaClassSource javaClassSource, EUmlField umlField) {
		FieldSource<JavaClassSource> fieldSource = javaClassSource.addField();
		RoasterUtil.addImport(javaClassSource, umlField.getType().getClassName());
		// @formatter:off
		fieldSource
			.setName(umlField.getName())
			.setPrivate()
			.setType(umlField.getType().getJavaName());
		// @formatter:on
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitClass(this);
	}

}
