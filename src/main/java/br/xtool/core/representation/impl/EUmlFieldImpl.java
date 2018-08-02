package br.xtool.core.representation.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import com.google.common.collect.Sets;

import br.xtool.core.representation.EUmlField;
import br.xtool.core.representation.EUmlFieldProperty;
import br.xtool.core.representation.EUmlFieldProperty.FieldPropertyType;
import br.xtool.core.util.RoasterUtil;
import net.sourceforge.plantuml.cucadiagram.Member;
import strman.Strman;

public class EUmlFieldImpl implements EUmlField {

	private Member member;

	public EUmlFieldImpl(Member member) {
		super();
		this.member = member;
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlField#getName()
	 */
	@Override
	public String getName() {
		return memberName();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlField#getType()
	 */
	@Override
	public FieldType getType() {
		if (this.isArray()) {
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
	 * @see br.xtool.core.representation.EUmlField#isId()
	 */
	@Override
	public boolean isId() {
		return StringUtils.equalsIgnoreCase(this.getName(), "id");
	}

	@Override
	public boolean isLong() {
		return this.getType().equals(FieldType.LONG) && !this.isId();
	}

	@Override
	public boolean isByteArray() {
		return getType().equals(FieldType.BYTE) && this.isArray();
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
	public boolean isString() {
		return getType().equals(FieldType.STRING);
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlField#isArray()
	 */
	@Override
	public boolean isArray() {
		return Strman.containsAll(memberType(), new String[] { "[", "]" });
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlField#getMinArrayLength()
	 */
	@Override
	public Optional<Integer> getMinArrayLength() {
		if (this.isArray()) {
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
	 * @see br.xtool.core.representation.EUmlField#getMaxArrayLength()
	 */
	@Override
	public Optional<Integer> getMaxArrayLength() {
		if (this.isArray()) {
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

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlField#hasProperty(br.xtool.core.representation.EUmlFieldProperty.FieldPropertyType)
	 */
	@Override
	public boolean hasProperty(FieldPropertyType property) {
		return this.getProperties().stream().anyMatch(prop -> prop.getFieldProperty().equals(property));
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlField#hasProperties()
	 */
	@Override
	public boolean hasProperties() {
		return !this.getProperties().isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see br.xtool.core.representation.EUmlField#getProperties()
	 */
	@Override
	public Set<EUmlFieldProperty> getProperties() {
		if (Strman.containsAll(memberType(), new String[] { "{", "}" })) {
			String[] propertiesBlock = Strman.between(memberType(), "{", "}");
			String[] propertiesItens = StringUtils.split(StringUtils.join(propertiesBlock), ",");
			if (propertiesItens.length > 1) {
				// @formatter:off
				return Stream.of(propertiesItens)
					.map(item -> new EUmlFieldPropertyImpl(this, item))
					.collect(Collectors.toSet());
				// @formatter:on
			}
			return Sets.newHashSet(new EUmlFieldPropertyImpl(this, propertiesBlock[0]));
		}
		return new HashSet<>();
	}

	@Override
	public FieldSource<JavaClassSource> convertToFieldSource(JavaClassSource javaClassSource) {
		FieldSource<JavaClassSource> fieldSource = javaClassSource.addField();
		RoasterUtil.addImport(javaClassSource, this.getType().getClassName());
		// @formatter:off
		return fieldSource
			.setName(this.getName())
			.setPrivate()
			.setType(this.getType().getJavaName());
		// @formatter:on
	}

	private String memberName() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[0]);
	}

	private String memberType() {
		return StringUtils.trim(StringUtils.split(this.member.getDisplay(false), ":")[1]);
	}

}
