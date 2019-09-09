package br.xtool.core.implementation.representation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.model.Annotation;
import org.jboss.forge.roaster.model.JavaDoc;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.SyntaxError;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jtwig.util.HtmlUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import br.xtool.core.Clog;
import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantClassRepresentation;
import br.xtool.core.representation.plantuml.PlantPackageRepresentation;
import br.xtool.core.representation.plantuml.PlantRelationshipRepresentation;
import br.xtool.core.representation.plantuml.PlantStereotypeRepresentation;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.cucadiagram.ILeaf;
import net.sourceforge.plantuml.cucadiagram.LeafType;
import net.sourceforge.plantuml.cucadiagram.Link;
import net.sourceforge.plantuml.cucadiagram.LinkArrow;
import net.sourceforge.plantuml.skin.VisibilityModifier;
import strman.Strman;

public class PlantClassRepresentationImpl implements PlantClassRepresentation {

	private PlantClassDiagramRepresentation umlClassDiagram;

	private ClassDiagram classDiagram;

	private Map<String, String> taggedValues;

	private ILeaf leaf;

	public PlantClassRepresentationImpl(PlantClassDiagramRepresentation umlClassDiagram, ClassDiagram classDiagram, ILeaf leaf) {
		this.umlClassDiagram = umlClassDiagram;
		this.classDiagram = classDiagram;
		this.leaf = leaf;
	}

	@Override
	public PlantClassDiagramRepresentation getClassDiagram() {
		return this.umlClassDiagram;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getFields()
	 */
	@Override
	public Collection<PlantClassFieldRepresentation> getFields() {
		// @formatter:off
		return this.leaf.getBodier().getFieldsToDisplay().stream()
				.filter(member -> StringUtils.isNotEmpty(member.getDisplay(false)))
				.filter(member -> Objects.nonNull(member.getVisibilityModifier()))
				.filter(member -> member.getVisibilityModifier().equals(VisibilityModifier.PRIVATE_FIELD))
				.map(member -> new PlantClassFieldRepresentationImpl(umlClassDiagram, member, this.getTaggedValues()))
				.collect(Collectors.toList());
		// @formatter:on 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getStereotypes()
	 */
	@Override
	public Set<PlantStereotypeRepresentation> getStereotypes() {
		// @formatter:off
		if(Objects.nonNull(this.leaf.getStereotype())) {
			return this.leaf.getStereotype().getLabels(false).stream()
					.map(value -> Strman.between(value, "<<", ">>"))
					.map(StringUtils::join)
					.map(value -> new PlantStereotypeRepresentationImpl(this, value))
					.collect(Collectors.toSet());
		}
		// @formatter:on
		return new HashSet<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getTaggedValues()
	 */
	@Override
	public Map<String, String> getTaggedValues() {
		if (Objects.isNull(this.taggedValues)) {
			// @formatter:off
			this.taggedValues = this.leaf.getBodier().getFieldsToDisplay().stream()
					.filter(member -> StringUtils.isNotEmpty(member.getDisplay(false)))
					.filter(member -> HtmlUtils.stripTags(StringUtils.trim(member.getDisplay(false))).startsWith("@"))
					.map(member -> HtmlUtils.stripTags(StringUtils.trim(member.getDisplay(false))))
					.map(rawTaggedValue -> StringUtils.split(rawTaggedValue, ":"))
					.filter(ach -> ach.length > 1)
					.collect(Collectors.toMap(tagValue -> HtmlUtils.stripTags(StringUtils.trim(tagValue[0])) , tagValue -> StringUtils.trim(tagValue[1])));
			// @formatter:on
		}
		return this.taggedValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getTaggedValue(java.lang.String)
	 */
	@Override
	public Optional<String> getTaggedValue(String key) {
		return Optional.ofNullable(this.getTaggedValues().get(String.format("@%s", key)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getTaggedValues(java.lang.String)
	 */
	@Override
	public Optional<String[]> getTaggedValueAsArray(String key) {
		String v = this.getTaggedValues().get(String.format("@%s", key));
		if (StringUtils.isNotEmpty(v)) {
			if (v.startsWith("[") && v.endsWith("]")) {
				String v1 = Strman.between(v, "[", "]")[0];
				if (StringUtils.isNotBlank(v1)) {
					// @formatter:off
					return Optional.of(
						Splitter.on(",")
							.trimResults()
							.splitToList(v1)
							.toArray(new String[]{})
					);
					// @formatter:on
				}
			}
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlClass#getRelationships()
	 */
	@Override
	public List<PlantRelationshipRepresentation> getRelationships() {

		// @formatter:off
		Collection<PlantRelationshipRepresentation> relationship1 = this.classDiagram.getEntityFactory().getLinks().stream()
				.filter(link -> link.getEntity2().getEntityType().equals(LeafType.CLASS))
				.filter(link -> link.getEntity1().getDisplay().asStringWithHiddenNewLine().equals(this.getName()))
				.map(link -> new PlantRelationshipRepresentationImpl(this, findPlantClassByName(link.getEntity2().getDisplay().asStringWithHiddenNewLine()), link, getEntity2Qualifier(link), getEntity1Qualifier(link)))
				.collect(Collectors.toList());
		// @formatter:on

		// @formatter:off
		Collection<PlantRelationshipRepresentation> relationship2 = this.classDiagram.getEntityFactory().getLinks().stream()
				.filter(link -> link.getEntity1().getEntityType().equals(LeafType.CLASS))
				.filter(link -> link.getEntity2().getDisplay().asStringWithHiddenNewLine().equals(this.getName()))
				.map(link -> new PlantRelationshipRepresentationImpl(this, findPlantClassByName(link.getEntity1().getDisplay().asStringWithHiddenNewLine()), link, getEntity1Qualifier(link), getEntity2Qualifier(link)))
				.collect(Collectors.toList());
		// @formatter:on

		Clog.printv("relationship1: ", relationship1.size(), " relationship2: ", relationship2.size());

		return ImmutableList.<PlantRelationshipRepresentation>builder().addAll(relationship1).addAll(relationship2).build();
	}

	private PlantClassRepresentation findPlantClassByName(String name) {
		// @formatter:off
		return this.umlClassDiagram.getClasses().stream()
			.filter(pClass -> pClass.getName().equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(String.format("Classe %s não encontrada", name)));
		// @formatter:on

	}

	private String getEntity1Qualifier(Link link) {
		return StringUtils.trim(link.getQualifier1());
	}

	private String getEntity2Qualifier(Link link) {
		return StringUtils.trim(link.getQualifier2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.forge.roaster.model.JavaType#getCanonicalName()
	 */
	@Override
	public String getCanonicalName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.forge.roaster.model.JavaType#getSyntaxErrors()
	 */
	@Override
	public List<SyntaxError> getSyntaxErrors() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.forge.roaster.model.JavaType#hasSyntaxErrors()
	 */
	@Override
	public boolean hasSyntaxErrors() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.forge.roaster.model.JavaType#isClass()
	 */
	@Override
	public boolean isClass() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.forge.roaster.model.JavaType#isEnum()
	 */
	@Override
	public boolean isEnum() {
		return false;
	}

	@Override
	public boolean isInterface() {
		return false;
	}

	@Override
	public boolean isAnnotation() {
		return false;
	}

	@Override
	public JavaType<?> getEnclosingType() {
		return null;
	}

	@Override
	public String toUnformattedString() {
		return null;
	}

	@Override
	public String getPackage() {
		return this.getUmlPackage().getName();
	}

	@Override
	public boolean isDefaultPackage() {
		return false;
	}

	@Override
	public List<? extends Annotation<JavaClassSource>> getAnnotations() {
		return new ArrayList<>();
	}

	@Override
	public boolean hasAnnotation(Class<? extends java.lang.annotation.Annotation> type) {
		return false;
	}

	@Override
	public boolean hasAnnotation(String type) {
		return false;
	}

	@Override
	public Annotation<JavaClassSource> getAnnotation(Class<? extends java.lang.annotation.Annotation> type) {
		return null;
	}

	@Override
	public Annotation<JavaClassSource> getAnnotation(String type) {
		return null;
	}

	@Override
	public JavaClassSource getOrigin() {
		return null;
	}

	@Override
	public Object getInternal() {
		return null;
	}

	@Override
	public JavaDoc<JavaClassSource> getJavaDoc() {
		return null;
	}

	@Override
	public boolean hasJavaDoc() {
		return false;
	}

	@Override
	public boolean isPackagePrivate() {
		return false;
	}

	@Override
	public boolean isPublic() {
		return true;
	}

	@Override
	public boolean isPrivate() {
		return false;
	}

	@Override
	public boolean isProtected() {
		return false;
	}

	@Override
	public Visibility getVisibility() {
		return null;
	}

	@Override
	public boolean hasStereotype(String stereotypeName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "PlantClassRepresentationImpl [" + (getName() != null ? "getName()=" + getName() : "") + "]";
	}

}
