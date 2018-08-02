package br.xtool.core.representation.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import com.google.common.collect.ImmutableSet;

import br.xtool.core.representation.EBootProject;
import br.xtool.core.representation.EUmlClass;
import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlPackage;
import br.xtool.core.representation.EUmlRelationship;
import br.xtool.core.representation.EUmlStereotype;
import br.xtool.core.util.RoasterUtil;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.cucadiagram.LeafType;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkDecor;
import strman.Strman;

public class EUmlClassImpl implements EUmlClass {

	private ClassDiagram classDiagram;

	private ILeaf leaf;

	public EUmlClassImpl(ClassDiagram classDiagram, ILeaf leaf) {
		super();
		this.classDiagram = classDiagram;
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#getRelationships()
	 */
	@Override
	public Set<EUmlRelationship> getRelationships() {
		return ImmutableSet.<EUmlRelationship>builder().addAll(iterateOverEntities1()).addAll(iterateOverEntities2()).build();
	}

	protected EUmlClass findClass(String className) {
		String error = "Classe '%s' não definida no pacote. Insira a definção da classe com os atributos correspondentes no pacote.";
		// @formatter:off
		return this.classDiagram.getGroups(false).stream()
			 .flatMap(groups -> groups.getLeafsDirect().stream())
			 .filter(leaf1 -> leaf1.getEntityType().equals(LeafType.CLASS))
			 .filter(leaf1 -> !leaf1.getDisplay().asStringWithHiddenNewLine().equals(this.getName()))
			 .filter(leaf1 -> leaf1.getDisplay().asStringWithHiddenNewLine().equals(className))
			 .map(_leaf -> new EUmlClassImpl(this.classDiagram, _leaf))
			 .findAny()
			 .orElseThrow(() -> new IllegalArgumentException(String.format(error, className)));
		 // @formatter:on
	}

	private Set<EUmlRelationship> iterateOverEntities1() {
		Predicate<Link> p1 = (link) -> link.getType().getDecor1().equals(LinkDecor.ARROW);
		Predicate<Link> p2 = (link) -> link.getType().getDecor1().equals(LinkDecor.NONE) && link.getType().getDecor2().equals(LinkDecor.NONE);
		Predicate<Link> p3 = (link) -> link.getType().getDecor2().equals(LinkDecor.COMPOSITION);
		Predicate<Link> p4 = (link) -> link.getType().getDecor1().equals(LinkDecor.COMPOSITION) && link.getType().getDecor2().equals(LinkDecor.ARROW);
		// @formatter:off
		return this.classDiagram.getEntityFactory().getLinks().stream()
			.filter(link -> link.getEntity1().getDisplay().asStringWithHiddenNewLine().equals(this.getName()))
			.filter(p1.or(p2).or(p3).or(p4))
			.map(link -> new EUmlRelationshipImpl(this, findClass(link.getEntity2().getDisplay().asStringWithHiddenNewLine()), link, getEntity2Qualifier(link), getEntity1Qualifier(link)))
			.collect(Collectors.toSet());
		// @formatter:on
	}

	private String getEntity1Qualifier(Link link) {
		return StringUtils.trim(link.getQualifier1());
	}

	private String getEntity2Qualifier(Link link) {
		return StringUtils.trim(link.getQualifier2());
	}

	private Set<EUmlRelationship> iterateOverEntities2() {
		Predicate<Link> p1 = (link) -> link.getType().getDecor2().equals(LinkDecor.ARROW);
		Predicate<Link> p2 = (link) -> link.getType().getDecor1().equals(LinkDecor.NONE) && link.getType().getDecor2().equals(LinkDecor.NONE);
		Predicate<Link> p3 = (link) -> link.getType().getDecor1().equals(LinkDecor.COMPOSITION);
		Predicate<Link> p4 = (link) -> link.getType().getDecor2().equals(LinkDecor.COMPOSITION) && link.getType().getDecor1().equals(LinkDecor.ARROW);
		// @formatter:off
		return this.classDiagram.getEntityFactory().getLinks().stream()
			.filter(link -> link.getEntity2().getDisplay().asStringWithHiddenNewLine().equals(this.getName()))
			.filter(p1.or(p2).or(p3).or(p4))
//			.map(link -> Pair.of(link, link.getEntity1().getDisplay().asStringWithHiddenNewLine()))
			.map(link -> new EUmlRelationshipImpl(this, findClass(link.getEntity1().getDisplay().asStringWithHiddenNewLine()), link, getEntity1Qualifier(link), getEntity2Qualifier(link)))
			.collect(Collectors.toSet());
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlClass#convertToJavaClassSource(br.xtool.core.representation.EBootProject)
	 */
	@Override
	public JavaClassSource convertToJavaClassSource(EBootProject bootProject) {
		// @formatter:off
		JavaClassSource javaClassSource = bootProject.getRoasterJavaUnits().stream()
			.filter(javaUnit -> javaUnit.getGoverningType().isClass())
			.filter(javaUnit -> javaUnit.getGoverningType().getName().equals(this.getName()))
			.map(javaUnit -> javaUnit.<JavaClassSource>getGoverningType())
			.findFirst()
			.orElseGet(() -> RoasterUtil.createJavaClassSource(this.getPackage().getName(),this.getName()));
		// @formatter:on
		this.getFields().stream().forEach(umlField -> umlField.convertToFieldSource(javaClassSource));
		return javaClassSource;
	}

	//	private void createOrUpdateFieldSource(JavaClassSource javaClassSource, EUmlField umlField) {
	//		FieldSource<JavaClassSource> fieldSource = javaClassSource.addField();
	//		RoasterUtil.addImport(javaClassSource, umlField.getType().getClassName());
//		// @formatter:off
//		fieldSource
//			.setName(umlField.getName())
//			.setPrivate()
//			.setType(umlField.getType().getJavaName());
//		// @formatter:on
	//	}

}
