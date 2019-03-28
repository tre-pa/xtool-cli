package br.xtool.core.implementation.representation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;

import br.xtool.core.representation.plantuml.PlantClassDiagramRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldRepresentation;
import br.xtool.core.representation.plantuml.PlantEnumRepresentation;
import br.xtool.core.representation.plantuml.PlantClassFieldPropertyRepresentation.FieldPropertyType;
import net.sourceforge.plantuml.cucadiagram.Member;
import strman.Strman;

public class PlantClassFieldRepresentationImpl implements PlantClassFieldRepresentation {

	private PlantClassDiagramRepresentation classDiagram;

	private Member member;

	private Map<String, String> taggedValues = new HashMap<>();

	public PlantClassFieldRepresentationImpl(PlantClassDiagramRepresentation classDiagram, Member member, Map<String, String> taggedValues) {
		super();
		this.classDiagram = classDiagram;
		this.member = member;
		this.taggedValues = taggedValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#getName()
	 */
	@Override
	public String getName() {
		return memberName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#getType()
	 */
	@Override
	public FieldType getType() {
		if (this.isEnum()) {
			// @formatter:off
			PlantEnumRepresentation plantEnum = this.classDiagram.getEnums()
					.stream()
					.filter(pEnum -> pEnum.getName().equals(this.memberType()))
					.findFirst()
					.get();
			// @formatter:on
			FieldType fieldType = FieldType.ENUM;
			fieldType.setJavaName(plantEnum.getName());
			fieldType.setClassName(plantEnum.getUmlPackage().getName().concat(".").concat(plantEnum.getName()));
			return fieldType;
		}
		if (this.hasMultiplicity()) {
			String type = StringUtils.substring(this.memberType(), 0, StringUtils.indexOf(this.memberType(), "[")).trim();
			return FieldType.valueOf(StringUtils.upperCase(type));
		}
		if (this.hasProperties()) {
			String type = StringUtils.substring(this.memberType(), 0, StringUtils.indexOf(this.memberType(), "{")).trim();
			return FieldType.valueOf(StringUtils.upperCase(type));
		}
		return FieldType.valueOf(StringUtils.upperCase(this.memberType()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#isId()
	 */
	@Override
	public boolean isId() {
		return this.getProperty(FieldPropertyType.ID).isPresent();
	}

	@Override
	public boolean isLong() {
		return this.getType().equals(FieldType.LONG);
	}

	@Override
	public boolean isByte() {
		return getType().equals(FieldType.BYTE);
	}

	@Override
	public boolean isBoolean() {
		return getType().equals(FieldType.BOOLEAN);
	}

	@Override
	public boolean isInteger() {
		return getType().equals(FieldType.INTEGER);
	}

	@Override
	public boolean isLocalDate() {
		return getType().equals(FieldType.LOCALDATE);
	}

	@Override
	public boolean isLocalDateTime() {
		return getType().equals(FieldType.LOCALDATETIME);
	}

	@Override
	public boolean isBigDecimal() {
		return getType().equals(FieldType.BIGDECIMAL);
	}

	@Override
	public boolean isBigInteger() {
		return getType().equals(FieldType.BIGINTEGER);
	}

	@Override
	public boolean isString() {
		return getType().equals(FieldType.STRING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EPlantField#isEnum()
	 */
	@Override
	public boolean isEnum() {
		return this.classDiagram.getEnums().stream().anyMatch(plantEnum -> plantEnum.getName().equals(this.memberType()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#isArray()
	 */
	@Override
	public boolean hasMultiplicity() {
		return Strman.containsAll(memberType(), new String[] { "[", "]" });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.xtool.core.representation.PlantFieldRepresentation#getEnumRepresentation()
	 */
	@Override
	public Optional<PlantEnumRepresentation> getPlantEnumRepresentation() {
		// @formatter:off
		return this.classDiagram.getEnums().stream()
				.filter(plantEnum -> plantEnum.getName().equals(this.getType().getJavaName()))
				.findFirst();
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#getMinArrayLength()
	 */
	@Override
	public Optional<Integer> getLowerBoundMultiplicity() {
		if (this.hasMultiplicity()) {
			Pattern arrayRangePattern = Pattern.compile("(\\d*)\\.\\.\\d*");
			String[] arrayMultiplicity = Strman.between(memberType(), "[", "]");
			Matcher matcher = arrayRangePattern.matcher(StringUtils.join(arrayMultiplicity));
			if (matcher.find()) {
				Integer min = Integer.parseInt(matcher.group(1));
				return Optional.of(min);
			}
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#getMaxArrayLength()
	 */
	@Override
	public Optional<Integer> getUpperBoundMultiplicity() {
		if (this.hasMultiplicity()) {
			String[] arrayMultiplicity = Strman.between(memberType(), "[", "]");
			String multiplicityValue = StringUtils.join(arrayMultiplicity);
			if (NumberUtils.isDigits(multiplicityValue)) return Optional.of(Integer.parseInt(multiplicityValue));
			Matcher matcher = Pattern.compile("\\d*\\.\\.(\\d*)").matcher(multiplicityValue);
			if (matcher.find()) {
				Integer max = Integer.parseInt(matcher.group(1));
				return Optional.of(max);
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<PlantClassFieldPropertyRepresentation> getProperty(FieldPropertyType type) {
		// @formatter:off
		return this.getProperties().stream()
				.filter(property -> property.getFieldProperty().equals(type))
				.findAny();
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#hasProperties()
	 */
	@Override
	public boolean hasProperties() {
		return !this.getProperties().isEmpty();
	}

	private Set<PlantClassFieldPropertyRepresentation> getProperties() {
		if (Strman.containsAll(memberType(), new String[] { "{", "}" })) {
			String[] propertiesBlock = Strman.between(memberType(), "{", "}");
			String[] propertiesItens = StringUtils.split(StringUtils.join(propertiesBlock), ",");
			if (propertiesItens.length > 1) {
				// @formatter:off
				return Stream.of(propertiesItens)
					.map(StringUtils::trim)
					.map(item -> new PlantClassFieldPropertyRepresentationImpl(this, item))
					.collect(Collectors.toSet());
				// @formatter:on
			}
			return Sets.newHashSet(new PlantClassFieldPropertyRepresentationImpl(this, propertiesBlock[0]));
		}
		return new HashSet<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#getTaggedValues()
	 */
	private Map<String, String> getTaggedValues() {
		// @formatter:off
		return this.taggedValues.entrySet().stream()
				.filter(map -> map.getKey().startsWith(String.format("@%s",this.getName())))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		// @formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.xtool.core.representation.EUmlField#getTaggedValues(java.lang.String)
	 */
	@Override
	public Optional<String[]> getTaggedValues(String key) {
		String v = this.getTaggedValues().get(String.format("@%s.%s", this.getName(), key));
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
	 * @see br.xtool.core.representation.EUmlField#getTaggedValue(java.lang.String)
	 */
	@Override
	public Optional<String> getTaggedValue(String key) {
		return Optional.ofNullable(this.getTaggedValues().get(String.format("@%s.%s", this.getName(), key)));
	}

	private String memberName() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[0]);
	}

	private String memberType() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[1]);
	}

}
